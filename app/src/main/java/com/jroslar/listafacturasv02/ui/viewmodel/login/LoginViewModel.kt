package com.jroslar.listafacturasv02.ui.viewmodel.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv02.data.model.UserModel
import com.jroslar.listafacturasv02.domain.LoginUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel: ViewModel() {
    private var _emailValue: MutableLiveData<String> = MutableLiveData()
    val emailValue: LiveData<String> get() = _emailValue
    private var _passwordValue: MutableLiveData<String> = MutableLiveData()
    val passwordValue: LiveData<String> get() = _passwordValue
    var _state: MutableLiveData<LoginResult?> = MutableLiveData()

    private object Injection: KoinComponent {
        val loginUseCase by inject<LoginUseCase>()
    }
    private val loginUseCase = Injection.loginUseCase

    fun validateData() {
        viewModelScope.launch {
            _state.postValue(LoginResult.LOADING)
            if (validateUsuario(emailValue.value?:"") && validatePassword(passwordValue.value?:"")) {
                _state.postValue(loginUseCase(
                    UserModel(emailValue.value!!, passwordValue.value!!)
                ))
            } else {
                _state.postValue(LoginResult.ERROR_DATA)
            }
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
        if (password.isNotEmpty()) {
            return true
        }
        return false
    }

    enum class LoginResult {
        LOADING,
        SUCCESS,
        ERROR_DATA,
        NO_VALID_DATA
    }
}