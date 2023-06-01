package com.jroslar.listafacturasv02.ui.viewmodel.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.domain.CreateAccountUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignupViewModel: ViewModel() {
    private var _emailValue: MutableLiveData<String> = MutableLiveData()
    val emailValue: LiveData<String> get() = _emailValue
    private var _passwordValue: MutableLiveData<String> = MutableLiveData()
    val passwordValue: LiveData<String> get() = _passwordValue
    private var _repeatPasswordValue: MutableLiveData<String> = MutableLiveData()
    val repeatPasswordValue: LiveData<String> get() = _repeatPasswordValue
    var _state: MutableLiveData<SignupResult> = MutableLiveData()

    private object Injection: KoinComponent {
        val createAccountUseCase by inject<CreateAccountUseCase>()
    }
    private val createAccountUseCase = Injection.createAccountUseCase

    fun comprobarDatos() {
        viewModelScope.launch {
            _state.postValue(SignupResult.LOADING)
            if (validateUsuario(emailValue.value?:"")
                && validatePassword(passwordValue.value?:"")
                && validateRepeatPassword(repeatPasswordValue.value?:"")) {
                createAccount()
            } else {
                _state.postValue(SignupResult.ERROR_DATA)
            }
        }
    }

    private suspend fun createAccount() {
        if (createAccountUseCase(UserModel(emailValue.value!!, passwordValue.value!!))) {
            _state.postValue(SignupResult.SUCCESS)
        } else {
            _state.postValue(SignupResult.FAIL)
        }
    }

    fun validateUsuario(usuario: String): Boolean {
        _emailValue.value = usuario
        if (Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
            return true
        }
        return false
    }

    fun validatePassword(password: String): Boolean {
        _passwordValue.value = password
        if (password.length >= 6) {
            return true
        }
        return false
    }

    fun validateRepeatPassword(password: String): Boolean {
        _repeatPasswordValue.value = password
        if (passwordValue.value.equals(password)) {
            return true
        }
        return false
    }

    enum class SignupResult {
        LOADING,
        SUCCESS,
        FAIL,
        ERROR_DATA
    }
}