package com.example.vozi001

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vozi001.ui.theme.VoziBlue
import com.example.vozi001.ui.theme.VoziWhite

@Composable
fun RegistrationSuccessScreen(
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Registro exitoso",
            modifier = Modifier.size(80.dp),
            tint = Color.Green
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¡Registro Exitoso!",
            style = MaterialTheme.typography.headlineLarge,
            color = VoziBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tu cuenta ha sido creada exitosamente.\nAhora puedes iniciar sesión con tus credenciales.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onContinueClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VoziBlue,
                contentColor = VoziWhite
            )
        ) {
            Text("Continuar al Login", style = MaterialTheme.typography.bodyLarge)
        }
    }
}