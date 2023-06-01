package com.jroslar.listafacturasv02.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.extensions.getResourceStringAndroid
import com.jroslar.listafacturasv02.core.extensions.onTextChanged
import com.jroslar.listafacturasv02.databinding.ActivitySignupBinding
import com.jroslar.listafacturasv02.ui.view.dashboard.DashBoardActivity
import com.jroslar.listafacturasv02.ui.viewmodel.login.SignupViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var _viewModel: SignupViewModel? = null
    private val viewModel get() = _viewModel!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = getViewModel()

        viewModel._state.observe(this) {
            when (it) {
                SignupViewModel.SignupResult.SUCCESS -> {
                    startActivity(Intent(this, DashBoardActivity::class.java))
                    finish()
                }
                SignupViewModel.SignupResult.ERROR_DATA -> {
                    validateError()
                }
                SignupViewModel.SignupResult.FAIL -> {
                    Toast.makeText(
                        baseContext,
                        R.string.errortietSignupDuplicate.getResourceStringAndroid(baseContext),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
                else -> {
                    //
                }
            }
        }

        initListeners()
    }

    private fun initListeners() {
        binding.btSignupRegistrarse.setOnClickListener {
            viewModel.comprobarDatos()
        }

        binding.btSignupIniciarSesion.setOnClickListener {
            finish()
        }

        binding.tietSignupUsuario.onTextChanged {
            validateUsuario(it)
        }

        binding.tietSignupContraseA.onTextChanged {
            validatePassword(it)
        }

        binding.tietSignupRepetirContraseA.onTextChanged {
            validateRepeatPassword(it)
        }

    }

    private fun validateError() {
        if (!binding.tilSignupUsuario.isErrorEnabled) {
            binding.tilSignupUsuario.error = R.string.errortietSignupUsuario.getResourceStringAndroid(baseContext)
        }
        if (!binding.tilSignupContraseA.isErrorEnabled) {
            setErrorContrasea()
        }
        if (!binding.tilSignupRepetirContraseA.isErrorEnabled) {
            setErrorRepetirContrasea()
        }
    }

    private fun validateUsuario(usuario: String) {
        if (viewModel.validateUsuario(usuario)) {
            binding.tilSignupUsuario.error = null
        } else {
            binding.tilSignupUsuario.error = R.string.errortietSignupUsuario.getResourceStringAndroid(baseContext)
        }
    }

    private fun validatePassword(password: String) {
        if (viewModel.validatePassword(password)) {
            binding.tilSignupContraseA.error = null
        } else {
            setErrorContrasea()
        }
    }

    private fun validateRepeatPassword(passsword: String) {
        if (viewModel.validateRepeatPassword(passsword)) {
            binding.tilSignupRepetirContraseA.error = null
        } else {
            setErrorRepetirContrasea()
        }
    }

    private fun setErrorRepetirContrasea() {
        binding.tilSignupRepetirContraseA.error = R.string.errortietSignupRepetirContraseA.getResourceStringAndroid(baseContext)
        binding.tilSignupRepetirContraseA.errorIconDrawable = null
    }

    private fun setErrorContrasea() {
        binding.tilSignupContraseA.error = R.string.errortietSignupContraseA.getResourceStringAndroid(baseContext)
        binding.tilSignupContraseA.errorIconDrawable = null
    }
}