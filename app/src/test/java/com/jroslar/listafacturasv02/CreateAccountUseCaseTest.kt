package com.jroslar.listafacturasv02


import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.data.network.FirebaseService
import com.jroslar.listafacturasv02.domain.CreateAccountUseCase
import com.jroslar.listafacturasv02.ui.viewmodel.login.SignupViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateAccountUseCaseTest {
    @RelaxedMockK
    private lateinit var firebaseService: FirebaseService

    lateinit var createAccountUseCase: CreateAccountUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        createAccountUseCase = CreateAccountUseCase(firebaseService)
    }

    @Test
    fun `when create account is success`() = runBlocking {
        //Give
        val user = UserModel("prueba@gmail.com", "prueba")
        coEvery { firebaseService.createAccount(user) } returns SignupViewModel.SignupResult.SUCCESS

        //When
        val response = createAccountUseCase(user)

        //Then
        coVerify(exactly = 1) { firebaseService.createAccount(user) }
        assert(response == SignupViewModel.SignupResult.SUCCESS)
    }

    @Test
    fun `when create account invalid email`() = runBlocking {
        //Give
        val user = UserModel("userModel.userEmail", "prueba")
        coEvery { firebaseService.createAccount(user) } returns SignupViewModel.SignupResult.ERROR_INVALID_EMAIL

        //When
        val response = createAccountUseCase(user)

        //Then
        coVerify(exactly = 1) { firebaseService.createAccount(user) }
        assert(response == SignupViewModel.SignupResult.ERROR_INVALID_EMAIL)
    }

    @Test
    fun `when create account user exists`() = runBlocking {
        //Give
        val user = UserModel("prueba@gmail.com", "prueba")
        coEvery { firebaseService.createAccount(user) } returns SignupViewModel.SignupResult.ERROR_USER_EXISTS

        //When
        val response = createAccountUseCase(user)

        //Then
        coVerify(exactly = 1) { firebaseService.createAccount(user) }
        assert(response == SignupViewModel.SignupResult.ERROR_USER_EXISTS)
    }
}