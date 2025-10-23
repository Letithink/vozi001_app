package com.example.vozi001

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.vozi001.ui.theme.Vozi001Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private var showLogoutButton by mutableStateOf(false)
    private var showRegistrationSuccess by mutableStateOf(false)
    private var showRegistrationScreen by mutableStateOf(false) // NUEVA VARIABLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            navegarAMainActivity()
            return
        }

        enableEdgeToEdge()
        setContent {
            Vozi001Theme {
                when {
                    showRegistrationSuccess -> {
                        RegistrationSuccessScreen {
                            showRegistrationSuccess = false
                            showRegistrationScreen = false // Volver al login
                        }
                    }
                    showRegistrationScreen -> {
                        RegistrationScreen(
                            onRegisterClicked = { nombre, email, password ->
                                registrarUsuario(nombre, email, password)
                            },
                            onBackClicked = {
                                showRegistrationScreen = false // Volver al login
                            }
                        )
                    }
                    else -> {
                        LoginScreen(
                            onLoginClicked = { email, password ->
                                iniciarSesion(email, password)
                            },
                            onRegisterClicked = { _, _, _ ->
                                // Cuando se presiona "Regístrate" en el login
                                showRegistrationScreen = true
                            },
                            showLogoutButton = showLogoutButton,
                            onLogoutClicked = {
                                cerrarSesion()
                            }
                        )
                    }
                }
            }
        }
    }

    // ... (el resto de tus funciones iniciarSesion, registrarUsuario, etc. se mantienen igual)
    private fun iniciarSesion(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        println("DEBUG: Intentando login con: $email")
        Toast.makeText(this, "Conectando...", Toast.LENGTH_SHORT).show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    println("DEBUG: Login EXITOSO - Usuario: ${auth.currentUser?.email}")
                    Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    navegarAMainActivity()
                } else {
                    println("DEBUG: Login FALLIDO - Error: ${task.exception?.message}")
                    val error = task.exception
                    val errorMessage = when {
                        error is FirebaseAuthInvalidUserException -> "Usuario no encontrado: $email"
                        error is FirebaseAuthInvalidCredentialsException -> "Contraseña incorrecta"
                        error?.message?.contains("network") == true -> "Error de red. Verifica tu conexión"
                        else -> "Error: ${error?.message ?: "Desconocido"}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun registrarUsuario(nombre: String, email: String, password: String) {
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
            return
        }

        println("DEBUG: Intentando registro con: $email")
        Toast.makeText(this, "Creando cuenta...", Toast.LENGTH_SHORT).show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    println("DEBUG: Registro EXITOSO - Usuario: ${auth.currentUser?.email}")
                    showRegistrationSuccess = true
                    Toast.makeText(this, "Cuenta creada exitosamente.", Toast.LENGTH_SHORT).show()
                } else {
                    println("DEBUG: Registro FALLIDO - Error: ${task.exception?.message}")
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> "La contraseña es demasiado débil."
                        is FirebaseAuthInvalidCredentialsException -> "El formato del email es inválido."
                        is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con este email."
                        else -> "Error en el registro: ${task.exception?.message}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun cerrarSesion() {
        auth.signOut()
        showLogoutButton = false
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
    }

    private fun navegarAMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}