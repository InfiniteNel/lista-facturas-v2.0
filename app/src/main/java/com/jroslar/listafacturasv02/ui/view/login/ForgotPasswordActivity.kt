package com.jroslar.listafacturasv02.ui.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.extensions.getResourceStringAndroid
import com.jroslar.listafacturasv02.core.extensions.onTextChanged
import com.jroslar.listafacturasv02.databinding.ActivityForgotPasswordBinding
import com.jroslar.listafacturasv02.ui.viewmodel.login.ForgotPasswordViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    private var _viewModel: ForgotPasswordViewModel? = null
    private val viewModel get() = _viewModel!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        _viewModel = getViewModel()

        viewModel._state.observe(this) {
            when (it) {
                ForgotPasswordViewModel.ForgotPasswordResult.LOADING -> {
                    setEnabledBt(false)
                }
                ForgotPasswordViewModel.ForgotPasswordResult.SUCCESS -> {
                    Toast.makeText(
                        baseContext,
                        R.string.forgotPasswordsendEmail.getResourceStringAndroid(baseContext),
                        Toast.LENGTH_SHORT,
                    ).show()
                    finish()
                }
                ForgotPasswordViewModel.ForgotPasswordResult.ERROR_DATA -> {
                    binding.tilForgotPasswordUsuario.error = R.string.errortietForgotPasswordEmail.getResourceStringAndroid(baseContext)
                    setEnabledBt(true)
                }
                ForgotPasswordViewModel.ForgotPasswordResult.NO_VALID_DATA -> {
                    binding.tilForgotPasswordUsuario.error = R.string.errortietForgotPasswordNotExit.getResourceStringAndroid(baseContext)
                    setEnabledBt(true)
                }
                else -> {
                    //
                }
            }
        }

        initListener()
    }

    private fun initListener() {
        binding.btForgotPasswordEnviar.setOnClickListener {
            viewModel.validateData()
        }

        binding.tietForgotPasswordUsuario.onTextChanged {
            validateEmail(it)
        }
    }

    private fun setEnabledBt(isEnabled: Boolean) {
        binding.btForgotPasswordEnviar.isEnabled = isEnabled
    }

    private fun validateEmail(email: String) {
        if (viewModel.validateEmail(email)) {
            binding.tilForgotPasswordUsuario.error = null
        } else {
            binding.tilForgotPasswordUsuario.error = R.string.errortietForgotPasswordEmail.getResourceStringAndroid(baseContext)
        }
    }
}