package com.jroslar.listafacturasv02.ui.viewmodel.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel: ViewModel() {
    private var _emailValue: MutableLiveData<String> = MutableLiveData()
    val emailValue: LiveData<String> get() = _emailValue
    private var _passwordValue: MutableLiveData<String> = MutableLiveData()
    val passwordValue: LiveData<String> get() = _passwordValue
    private var _repeatPasswordValue: MutableLiveData<String> = MutableLiveData()
    val repeatPasswordValue: LiveData<String> get() = _repeatPasswordValue

    fun comprobarDatos(): Boolean {
        if (validateUsuario(emailValue.value?:"") && validatePassword(passwordValue.value?:"") && validateRepeatPassword(repeatPasswordValue.value?:"")) {
            return true
        }
        return false
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
}