package com.jroslar.listafacturasv02

import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.data.network.FirebaseService
import com.jroslar.listafacturasv02.domain.LoginUseCase
import com.jroslar.listafacturasv02.ui.viewmodel.login.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    @RelaxedMockK
    private lateinit var firebaseService: FirebaseService

    lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        loginUseCase = LoginUseCase(firebaseService)
    }

    @Test
    fun `when login is success`() = runBlocking {
        //Give
        val user = UserModel("prueba@gmail.com", "prueba")
        coEvery { firebaseService.login(user) } returns LoginViewModel.LoginResult.SUCCESS

        //When
        val response = loginUseCase(user)

        //Then
        coVerify(exactly = 1) { firebaseService.login(user) }
        assert(response == LoginViewModel.LoginResult.SUCCESS)
    }

    @Test
    fun `when login invalid data`() = runBlocking {
        //Give
        val user = UserModel("prueba.com", "prueba")
        coEvery { firebaseService.login(user) } returns LoginViewModel.LoginResult.NO_VALID_DATA

        //When
        val response = loginUseCase(user)

        //Then
        coVerify(exactly = 1) { firebaseService.login(user) }
        assert(response == LoginViewModel.LoginResult.NO_VALID_DATA)
    }
}