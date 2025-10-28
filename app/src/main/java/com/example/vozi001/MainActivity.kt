package com.example.vozi001


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import com.example.vozi001.ui.theme.Vozi001Theme
import com.example.vozi001.utils.PermissionHelper
import com.example.vozi001.viewmodel.SessionViewModel
import com.google.firebase.auth.FirebaseAuth

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import com.example.vozi001.utils.UserManager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.vozi001.ui.composables.roles.ChildMainScreen
import com.example.vozi001.ui.composables.roles.ParentMainScreen
import com.example.vozi001.ui.composables.roles.TherapistMainScreen
import com.example.vozi001.ui.composables.common.LoadingScreen
import com.example.vozi001.ui.composables.common.ErrorScreen
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.perf.performance
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val sessionViewModel: SessionViewModel by viewModels()
    private var userRole by mutableStateOf<String?>(null)
    private var isLoading by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar Performance Monitoring (solo debug)
        if (BuildConfig.DEBUG) {
            try {
                val isPerfEnabled = Firebase.performance.isPerformanceCollectionEnabled
                println("DEBUG: Performance Monitoring Enabled: $isPerfEnabled")
            } catch (e: Exception) {
                println("DEBUG: Error checking Performance Monitoring: ${e.message}")
            }
        }

        setContent {
            Vozi001Theme {
                // Cargar el rol del usuario
                LaunchedEffect(Unit) {
                    loadUserRole()
                }

                when {
                    isLoading -> {
                        LoadingScreen()
                    }
                    userRole == "niño" -> {
                        ChildMainScreen(
                            onSignOut = { cerrarSesion() },
                            onStartRecording = {
                                if (PermissionHelper.hasAudioRecordingPermission(this)) {
                                    startActivity(Intent(this, RecordingActivity::class.java))
                                } else {
                                    PermissionHelper.requestAudioRecordingPermission(this)
                                }
                            }
                        )
                    }
                    userRole == "padre" -> {
                        ParentMainScreen(
                            onSignOut = { cerrarSesion() }
                        )
                    }
                    userRole == "terapeuta" -> {
                        TherapistMainScreen(
                            onSignOut = { cerrarSesion() }
                        )
                    }
                    else -> {
                        // Rol no reconocido o error
                        val coroutineScope = rememberCoroutineScope()
                        ErrorScreen(

                            message = "Error al cargar el perfil de usuario",
                            onRetry = {
                                coroutineScope.launch {
                                    loadUserRole()
                                }
                            },
                            onSignOut = { cerrarSesion() }
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadUserRole() {
        isLoading = true
        val uid = UserManager.getCurrentUserUid()
        if (uid != null) {
            userRole = withContext(Dispatchers.IO) {
                UserManager.getUserRole(uid)
            }
        }
        isLoading = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionHelper.RECORD_AUDIO_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this, RecordingActivity::class.java))
                }
            }
        }
    }


    private fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}