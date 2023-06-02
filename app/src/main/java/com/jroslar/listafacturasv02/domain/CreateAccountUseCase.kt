package com.jroslar.listafacturasv02.domain

import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.data.network.FirebaseService
import com.jroslar.listafacturasv02.ui.viewmodel.login.SignupViewModel


class CreateAccountUseCase constructor(
    private val firebaseService: FirebaseService
) {

    suspend operator fun invoke(userModel: UserModel): SignupViewModel.SignupResult {
        return firebaseService.createAccount(userModel)
    }
}