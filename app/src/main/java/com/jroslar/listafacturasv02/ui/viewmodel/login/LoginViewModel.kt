package com.jroslar.listafacturasv02.ui.viewmodel.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private var _emailValue: MutableLiveData<String> = MutableLiveData()
    val emailValue: LiveData<String> get() = _emailValue
    private var _passwordValue: MutableLiveData<String> = MutableLiveData()
    val passwordValue: LiveData<String> get() = _passwordValue

    fun validateData(): Boolean {
        if (validateUsuario(emailValue.value?:"") && validatePassword(passwordValue.value?:"")) {
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
        if (password.isNotEmpty()) {
            return true
        }
        return false
    }
}