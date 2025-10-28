package com.example.vozi001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.vozi001.service.AudioRecordingService
import com.example.vozi001.ui.composables.recording.RecordingScreen
import com.example.vozi001.ui.theme.Vozi001Theme
import com.example.vozi001.viewmodel.SessionViewModel

class RecordingActivity : ComponentActivity() {

    private val sessionViewModel: SessionViewModel by viewModels()
    private lateinit var audioRecordingService: AudioRecordingService

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permiso concedido, puedes grabar
        } else {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioRecordingService = AudioRecordingService(this)

        setContent {
            Vozi001Theme {
                RecordingScreen(
                    sessionViewModel = sessionViewModel,
                    audioRecordingService = audioRecordingService,
                    onBack = { finish() },
                    onRequestPermission = { permission ->
                        requestPermissionLauncher.launch(permission)
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioRecordingService.cleanup()
    }
}