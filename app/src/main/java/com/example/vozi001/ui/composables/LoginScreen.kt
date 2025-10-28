package com.example.vozi001.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.vozi001.ui.theme.VoziBlue
import com.example.vozi001.ui.theme.VoziWhite
import com.example.vozi001.ui.theme.VoziBlack

@Composable
fun LoginScreen(
    onLoginClicked: (String, String) -> Unit,
    onRegisterClicked: (String, String, String) -> Unit,
    showLogoutButton: Boolean,
    onLogoutClicked: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a VOZI",
            style = MaterialTheme.typography.headlineLarge,
            color = VoziBlue
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- CAMPO DE NOMBRE ---
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = VoziBlue,
                unfocusedTextColor = VoziBlue,
                cursorColor = VoziBlue,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = VoziBlue,
                unfocusedBorderColor = VoziBlack,
                focusedLabelColor = VoziBlue,
                unfocusedLabelColor = VoziBlack
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO DE EMAIL ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = VoziBlue,
                unfocusedTextColor = VoziBlue,
                cursorColor = VoziBlue,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = VoziBlue,
                unfocusedBorderColor = VoziBlack,
                focusedLabelColor = VoziBlue,
                unfocusedLabelColor = VoziBlack
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO DE CONTRASEÑA ---
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description, tint = VoziBlack)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = VoziBlue,
                unfocusedTextColor = VoziBlue,
                cursorColor = VoziBlue,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = VoziBlue,
                unfocusedBorderColor = VoziBlack,
                focusedLabelColor = VoziBlue,
                unfocusedLabelColor = VoziBlack
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÓN DE LOGIN ---
        Button(
            onClick = { onLoginClicked(email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = VoziBlue,
                contentColor = VoziWhite
            )
        ) {
            Text("Iniciar Sesión")
        }

        // --- BOTÓN DE REGISTRO ---
        TextButton(
            onClick = {
                onRegisterClicked(name, email, password)
            }
        ) {
            Text("¿No tienes cuenta? Regístrate", color = VoziBlue)
        }

        // --- BOTÓN DE CERRAR SESIÓN ---
        if (showLogoutButton) {
            Spacer(modifier = Modifier.height(40.dp))

            OutlinedButton(
                onClick = onLogoutClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFE3F2FD),
                    contentColor = VoziBlue
                ),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión")
            }
        }
    }
}