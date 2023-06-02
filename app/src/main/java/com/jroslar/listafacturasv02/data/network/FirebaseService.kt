package com.jroslar.listafacturasv02.data.network

import com.google.firebase.auth.*
import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.ui.viewmodel.login.ForgotPasswordViewModel
import com.jroslar.listafacturasv02.ui.viewmodel.login.LoginViewModel
import com.jroslar.listafacturasv02.ui.viewmodel.login.SignupViewModel
import kotlinx.coroutines.tasks.await

class FirebaseService constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun createAccount(userModel: UserModel): SignupViewModel.SignupResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(userModel.userEmail, userModel.userPassword).await()
            SignupViewModel.SignupResult.SUCCESS
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            SignupViewModel.SignupResult.ERROR_INVALID_EMAIL
        } catch(e: FirebaseAuthUserCollisionException) {
            SignupViewModel.SignupResult.ERROR_USER_EXISTS
        } catch (e: FirebaseAuthException) {
            SignupViewModel.SignupResult.FAIL
        }
    }

    suspend fun login(userModel: UserModel): LoginViewModel.LoginResult = kotlin.runCatching {
        firebaseAuth.signInWithEmailAndPassword(userModel.userEmail, userModel.userPassword).await()
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
