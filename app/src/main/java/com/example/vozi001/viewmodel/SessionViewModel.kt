package com.example.vozi001.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow // Importar esto para el patrón MVVM
import kotlinx.coroutines.launch
import java.util.*



class SessionViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _currentSession = MutableStateFlow<String?>(null)
    val currentSession: StateFlow<String?> = _currentSession.asStateFlow() // Exponer como StateFlow (solo lectura)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow() // Exponer como StateFlow (solo lectura)

    // Versión Mutable (interna)
    private val _errorMessage = MutableStateFlow<String?>(null)

    // Versión Inmutable (pública), lo que consume la vista
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * ✅ FUNCIÓN CORREGIDA: Única vía para establecer un mensaje de error.
     * La mutación (.value = ...) ocurre aquí, dentro del control del ViewModel.
     */
    fun setError(message: String?) {
        _errorMessage.value = message
    }

    /**
     * Función que llama a setError para limpiarlo.
     */
    fun clearError() {
        setError(null)
    }

    fun clearCurrentSession() {
        _currentSession.value = null
    }

    fun createSession(
        childId: String,
        audioDuration: Long,
        audioFileName: String? = null
    ) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val sessionData = hashMapOf(
                    "childId" to childId,
                    "duration" to audioDuration,
                    "timestamp" to com.google.firebase.Timestamp.now(),
                    "audioFileName" to audioFileName,
                    "status" to "completed"
                )

                println("DEBUG: Intentando guardar sesión en Firestore...")
                println("DEBUG: Datos: $sessionData")
                println("DEBUG: Usuario autenticado: ${FirebaseAuth.getInstance().currentUser?.uid}")

                db.collection("sessions")
                    .add(sessionData)
                    .addOnSuccessListener { documentReference ->
                        println("DEBUG: Sesión creada exitosamente: ${documentReference.id}")
                        _currentSession.value = documentReference.id
                        _isLoading.value = false
                    }
                    .addOnFailureListener { e ->
                        println("DEBUG: Error Firestore: ${e.message}")
                        println("DEBUG: Error tipo: ${e.javaClass.simpleName}")
                        _errorMessage.value = "Error al crear sesión: ${e.message}"
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                println("DEBUG: Excepción general: ${e.message}")
                _errorMessage.value = "Error: ${e.message}"
                _isLoading.value = false
            }
        }

    }
}