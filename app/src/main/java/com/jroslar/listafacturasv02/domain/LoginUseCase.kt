package com.jroslar.listafacturasv02.domain

import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.data.network.FirebaseService
import com.jroslar.listafacturasv02.ui.viewmodel.login.LoginViewModel


class LoginUseCase constructor(
    private val firebaseService: FirebaseService
){

    suspend operator fun invoke(userModel: UserModel): LoginViewModel.LoginResult {
        return firebaseService.login(userModel)
    }
}