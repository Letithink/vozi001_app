package com.example.vozi001.ui.composables.recording

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.vozi001.service.AudioRecordingService
import com.example.vozi001.viewmodel.SessionViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Timer
import kotlin.concurrent.timerTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingScreen(
    sessionViewModel: SessionViewModel,
    audioRecordingService: AudioRecordingService,
    onBack: () -> Unit,
    onRequestPermission: (String) -> Unit
) {
    var isRecording by remember { mutableStateOf(false) }
    var recordingTime by remember { mutableStateOf(0L) }
    var timer: Timer? by remember { mutableStateOf(null) }

    val context = LocalContext.current
    val hasRecordPermission = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Observar el estado de la sesión
    val currentSession by sessionViewModel.currentSession.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()
    val errorMessage by sessionViewModel.errorMessage.collectAsState()

    // Cuando se crea una sesión exitosamente, volver a MainActivity
    LaunchedEffect(currentSession) {
        currentSession?.let {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isRecording) "Grabando..." else "Grabar Audio",
                        color = if (isRecording) Color.Red else MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        enabled = !isRecording && !isLoading
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isRecording) Color(0x22FF0000) else MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Indicador visual de grabación
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = "Micrófono",
                    modifier = Modifier.size(80.dp),
                    tint = if (isRecording) Color.Red else MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Temporizador
            Text(
                text = formatTime(recordingTime),
                style = MaterialTheme.typography.displayMedium,
                color = if (isRecording) Color.Red else MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isRecording) "Grabando... Habla ahora" else "Presiona para comenzar a grabar",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Botón de grabación principal
            Button(
                onClick = {
                    if (!hasRecordPermission) {
                        onRequestPermission(Manifest.permission.RECORD_AUDIO)
                        return@Button
                    }

                    if (!isRecording) {
                        // Iniciar grabación
                        val result = audioRecordingService.startRecording()
                        if (result.isSuccess) {
                            isRecording = true
                            recordingTime = 0L
                            sessionViewModel.clearError()

                            // Iniciar temporizador
                            timer = Timer().apply {
                                scheduleAtFixedRate(timerTask {
                                    recordingTime++
                                }, 1000, 1000)
                            }
                        } else {
                            sessionViewModel.setError("Error al iniciar grabación: ${result.exceptionOrNull()?.message}")
                        }
                    } else {
                        // Detener grabación
                        val result = audioRecordingService.stopRecording()
                        isRecording = false
                        timer?.cancel()
                        timer = null

                        result.onSuccess { file ->
                            // Crear sesión en Firestore (sin subir archivo)
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            currentUser?.uid?.let { uid ->
                                sessionViewModel.createSession(
                                    childId = uid,
                                    audioDuration = recordingTime * 1000, // en milisegundos
                                    audioFileName = file?.name // Guardar nombre local opcional
                                )
                            }
                        }.onFailure { exception ->
                            sessionViewModel.setError("Error al guardar grabación: ${exception.message}")
                        }
                    }
                },
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRecording) Color.Red else MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                enabled = !isLoading && hasRecordPermission
            ) {
                Icon(
                    imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = if (isRecording) "Detener grabación" else "Comenzar grabación",
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mensaje de permisos
            if (!hasRecordPermission) {
                Text(
                    text = "Se necesita permiso de micrófono para grabar audio",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    onRequestPermission(Manifest.permission.RECORD_AUDIO)
                }) {
                    Text("Solicitar Permiso")
                }
            }

            // Loading durante el procesamiento
            if (isLoading) {
                Spacer(modifier = Modifier.height(24.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Guardando sesión...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Mostrar errores
            errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Error",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { sessionViewModel.clearError() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onErrorContainer,
                                contentColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text("Entendido")
                        }
                    }
                }
            }
        }
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}