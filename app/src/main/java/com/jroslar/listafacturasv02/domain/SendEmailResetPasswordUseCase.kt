package com.jroslar.listafacturasv02.domain

import com.jroslar.listafacturasv02.data.network.FirebaseService
import com.jroslar.listafacturasv02.ui.viewmodel.login.ForgotPasswordViewModel

class SendEmailResetPasswordUseCase constructor(
    private val firebaseService: FirebaseService
){

    suspend operator fun invoke(email: String): ForgotPasswordViewModel.ForgotPasswordResult {
        return firebaseService.sendEmailResetPassword(email)
    }
}