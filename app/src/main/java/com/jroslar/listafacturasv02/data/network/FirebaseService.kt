package com.jroslar.listafacturasv02.data.network

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await

class FirebaseService constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun createAccount(email: String, password: String): AuthResult? {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            null
        }
    }
}