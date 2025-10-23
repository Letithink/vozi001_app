package com.example.vozi001

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vozi001.ui.theme.Vozi001Theme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            Vozi001Theme {
                // Pantalla principal con botón de cerrar sesión
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // BOTÓN GRANDE DE CERRAR SESIÓN
                    Button(
                        onClick = {
                            cerrarSesion()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(bottom = 20.dp),
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

                    // Tu contenido principal de la app
                    Text(
                        text = "¡Bienvenido a V😊ZI!",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Has iniciado sesión exitosamente",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    // Agrega aquí el resto de tu contenido...
                }
            }
        }
    }

    private fun cerrarSesion() {
        auth.signOut()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}