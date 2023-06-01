package com.jroslar.listafacturasv02.domain

import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.data.network.FirebaseService


class CreateAccountUseCase constructor(
    private val firebaseService: FirebaseService
) {

    suspend operator fun invoke(userModel: UserModel): Boolean {
        return firebaseService.createAccount(userModel.userEmail, userModel.userPassword) != null
    }
}