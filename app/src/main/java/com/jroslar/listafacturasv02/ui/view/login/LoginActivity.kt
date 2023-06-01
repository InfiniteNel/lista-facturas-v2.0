package com.jroslar.listafacturasv02.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.extensions.getResourceStringAndroid
import com.jroslar.listafacturasv02.core.extensions.onTextChanged
import com.jroslar.listafacturasv02.databinding.ActivityLoginBinding
import com.jroslar.listafacturasv02.ui.App
import com.jroslar.listafacturasv02.ui.view.dashboard.DashBoardActivity
import com.jroslar.listafacturasv02.ui.viewmodel.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var _viewModel: LoginViewModel? = null
    private val viewModel get() = _viewModel!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = getViewModel()

        viewModel._state.observe(this) {
            when (it) {
                LoginViewModel.LoginResult.LOADING -> {
                    setEnabledBt(false)
                }
                LoginViewModel.LoginResult.SUCCESS -> {
                    dataSaved()
                    startActivity(Intent(this, DashBoardActivity::class.java))
                }
                LoginViewModel.LoginResult.ERROR_DATA -> {
                    validateError()
                    setEnabledBt(true)
                }
                LoginViewModel.LoginResult.NO_VALID_DATA -> {
                    Toast.makeText(
                        baseContext,
                        R.string.errortietLoginNotExit.getResourceStringAndroid(baseContext),
                        Toast.LENGTH_SHORT,
                    ).show()
                    setEnabledBt(true)
                }
                else -> {
                    //
                }
            }
        }

        initListeners()
    }

    override fun onStart() {
        super.onStart()

        setEnabledBt(true)

        val email = App.prefs.email
        val password = App.prefs.password

        if (email.isNotEmpty() && password.isNotEmpty()) {
            binding.chLoginRecordarContraseA.isChecked = true
            binding.tietLoginUsuario.setText(email)
            binding.tietLoginContraseA.setText(password)
        } else {
            binding.chLoginRecordarContraseA.isChecked = false
            binding.tietLoginUsuario.text = null
            binding.tietLoginContraseA.text = null
            binding.tilLoginUsuario.isErrorEnabled = false
            binding.tilLoginContraseA.isErrorEnabled = false
        }
    }

    private fun initListeners() {
        binding.btLoginEntrar.setOnClickListener {
            viewModel.validateData()
        }

        binding.chLoginRecordarContraseA.setOnClickListener {
            if (!binding.chLoginRecordarContraseA.isChecked) {
                App.prefs.email = ""
                App.prefs.password = ""
            }
        }

        binding.chLoginDatosOlvidados.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btLoginRegistrarte.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.tietLoginUsuario.onTextChanged {
            validateUsuario(it)
        }

        binding.tietLoginContraseA.onTextChanged {
            validatePassword(it)
        }
    }

    private fun setEnabledBt(isEnabled: Boolean) {
        binding.btLoginEntrar.isEnabled = isEnabled
        binding.btLoginRegistrarte.isEnabled = isEnabled
    }

    private fun dataSaved() {

        if (binding.chLoginRecordarContraseA.isChecked) {
            App.prefs.email = viewModel.emailValue.value!!
            App.prefs.password = viewModel.passwordValue.value!!
        } else {
            App.prefs.email = ""
            App.prefs.password = ""
        }
    }

    private fun validateError() {
        validateUsuario(binding.tietLoginUsuario.text.toString())
        validatePassword(binding.tietLoginContraseA.text.toString())
    }

    private fun setErrorContrasea() {
        binding.tilLoginContraseA.error = R.string.errortietLoginContraseA.getResourceStringAndroid(baseContext)
        binding.tilLoginContraseA.errorIconDrawable = null
    }

    private fun validatePassword(password: String) {
        if (viewModel.validatePassword(password)) {
            binding.tilLoginContraseA.error = null
        } else {
            setErrorContrasea()
        }
    }

    private fun validateUsuario(usuario: String) {
        if (viewModel.validateUsuario(usuario)) {
            binding.tilLoginUsuario.error = null
        } else {
            binding.tilLoginUsuario.error = R.string.errortietLoginUsuario.getResourceStringAndroid(baseContext)
        }
    }
}