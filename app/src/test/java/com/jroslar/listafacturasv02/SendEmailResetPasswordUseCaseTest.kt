package com.jroslar.listafacturasv02

import com.jroslar.listafacturasv02.data.network.FirebaseService
import com.jroslar.listafacturasv02.domain.SendEmailResetPasswordUseCase
import com.jroslar.listafacturasv02.ui.viewmodel.login.ForgotPasswordViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SendEmailResetPasswordUseCaseTest {
    @RelaxedMockK
    private lateinit var firebaseService: FirebaseService

    lateinit var sendEmailResetPasswordUseCase: SendEmailResetPasswordUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        sendEmailResetPasswordUseCase = SendEmailResetPasswordUseCase(firebaseService)
    }

    @Test
    fun `when send email reset password is success`() = runBlocking {
        //Give
        val email = "prueba@gmail.com"
        coEvery { firebaseService.sendEmailResetPassword(email) } returns ForgotPasswordViewModel.ForgotPasswordResult.SUCCESS

        //When
        val response = sendEmailResetPasswordUseCase(email)

        //Then
        coVerify(exactly = 1) { firebaseService.sendEmailResetPassword(email) }
        assert(response == ForgotPasswordViewModel.ForgotPasswordResult.SUCCESS)
    }

    @Test
    fun `when send email reset password invalid email`() = runBlocking {
        //Give
        val email = "prueba@gmail.com"
        coEvery { firebaseService.sendEmailResetPassword(email) } returns ForgotPasswordViewModel.ForgotPasswordResult.NO_VALID_DATA

        //When
        val response = sendEmailResetPasswordUseCase(email)

        //Then
        coVerify(exactly = 1) { firebaseService.sendEmailResetPassword(email) }
        assert(response == ForgotPasswordViewModel.ForgotPasswordResult.NO_VALID_DATA)
    }
}