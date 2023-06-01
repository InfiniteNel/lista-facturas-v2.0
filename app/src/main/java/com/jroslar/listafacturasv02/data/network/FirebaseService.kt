package com.jroslar.listafacturasv02.data.network

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.jroslar.listafacturasv02.ui.viewmodel.login.ForgotPasswordViewModel
import com.jroslar.listafacturasv02.ui.viewmodel.login.LoginViewModel
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

    suspend fun login(email: String, password: String): LoginViewModel.LoginResult = kotlin.runCatching {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    suspend fun sendEmailResetPassword(email: String): ForgotPasswordViewModel.ForgotPasswordResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            ForgotPasswordViewModel.ForgotPasswordResult.SUCCESS
        } catch (e: FirebaseAuthException) {
            ForgotPasswordViewModel.ForgotPasswordResult.NO_VALID_DATA
        }
    }
}

private fun Result<AuthResult>.toLoginResult(): LoginViewModel.LoginResult = when (getOrNull()) {
    null -> {
        LoginViewModel.LoginResult.NO_VALID_DATA
    }
    else -> {
        LoginViewModel.LoginResult.SUCCESS
    }
}
