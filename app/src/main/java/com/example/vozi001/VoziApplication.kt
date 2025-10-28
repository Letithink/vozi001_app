package com.example.vozi001


import android.app.Application
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.perf.ktx.performance

class VoziApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Inicializar Firebase
        Firebase.initialize(this)

        // Configurar Performance Monitoring
        setupPerformanceMonitoring()
    }

    private fun setupPerformanceMonitoring() {
        // Firebase Performance se inicializa automáticamente
        val perf = Firebase.performance

        // Log para verificar inicialización (solo en debug)
        if (BuildConfig.DEBUG) {
            println("DEBUG: Firebase Performance Monitoring inicializado")
            println("DEBUG: Performance Collection Enabled: ${Firebase.performance.isPerformanceCollectionEnabled}")
        }
    }
}