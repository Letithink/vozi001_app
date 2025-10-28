package com.example.vozi001.utils



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object UserManager {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getUserRole(uid: String): String? {
        return try {
            val document = db.collection("users").document(uid).get().await()
            document.getString("rol")
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserData(uid: String): Map<String, Any>? {
        return try {
            val document = db.collection("users").document(uid).get().await()
            document.data
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }
}