package com.example.vozi001

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun RegistrationScreen(
    onRegisterClicked: (String, String, String) -> Unit,
    onBackClicked: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón para regresar al login
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Regresar al login",
                tint = VoziBlue
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineLarge,
            color = VoziBlue,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
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

        // Campo de email
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

        // Campo de contraseña
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
                    Icon(imageVector = image, description, tint = VoziBlack)
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

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, description, tint = VoziBlack)
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

        // Botón de registro
        Button(
            onClick = {
                if (password == confirmPassword) {
                    onRegisterClicked(name, email, password)
                } else {
                    // Aquí podrías mostrar un Toast o mensaje de error
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = VoziBlue,
                contentColor = VoziWhite
            )
        ) {
            Text("Crear Cuenta")
        }
    }
}