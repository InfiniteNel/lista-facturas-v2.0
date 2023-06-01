package com.jroslar.listafacturasv02.ui.viewmodel.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv02.domain.SendEmailResetPasswordUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ForgotPasswordViewModel: ViewModel() {
    private var _emailValue: MutableLiveData<String> = MutableLiveData()
    val emailValue: LiveData<String> get() = _emailValue

    var _state: MutableLiveData<ForgotPasswordResult?> = MutableLiveData()

    private object Injection: KoinComponent {
        val sendEmailResetPasswordUseCase by inject<SendEmailResetPasswordUseCase>()
    }
    private val sendEmailResetPasswordUseCase = Injection.sendEmailResetPasswordUseCase

    fun validateData() {
        viewModelScope.launch {
            _state.postValue(ForgotPasswordResult.LOADING)
            if (validateEmail(emailValue.value?:"")) {
                _state.postValue(sendEmailResetPasswordUseCase(emailValue.value!!))
            } else {
                _state.postValue(ForgotPasswordResult.ERROR_DATA)
            }
        }
    }

    fun validateEmail(email: String): Boolean {
        _emailValue.value = email
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        }
        return false
    }

    enum class ForgotPasswordResult {
        LOADING,
        SUCCESS,
        ERROR_DATA,
        NO_VALID_DATA
    }
}