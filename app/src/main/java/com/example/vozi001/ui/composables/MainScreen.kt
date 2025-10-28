package com.example.vozi001.ui.composables


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vozi001.ui.theme.VoziBlue
import com.example.vozi001.ui.theme.VoziWhite

@Composable
fun MainScreen(
    onSignOut: () -> Unit,
    onStartRecording: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón de grabar
        Button(
            onClick = onStartRecording,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VoziBlue,
                contentColor = VoziWhite
            )
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Grabar audio",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("GRABAR AUDIO")
        }

        // Botón de cerrar sesión
        Button(
            onClick = onSignOut,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Cerrar sesión",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("CERRAR SESIÓN")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¡Bienvenido a V😊ZI!",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Has iniciado sesión exitosamente",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}