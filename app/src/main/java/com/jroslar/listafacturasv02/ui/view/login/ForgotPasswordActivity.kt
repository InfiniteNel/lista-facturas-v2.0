package com.jroslar.listafacturasv02.ui.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.Extensions.Companion.getResourceStringAndroid
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

        binding.btForgotPasswordEnviar.setOnClickListener {
            if (viewModel.validateData()) {
                auth.sendPasswordResetEmail(viewModel.emailValue.value!!)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("FireBase", "sendPasswordResetEmail:success")
                            Toast.makeText(
                                baseContext,
                                R.string.forgotPasswordsendEmail.getResourceStringAndroid(baseContext),
                                Toast.LENGTH_SHORT,
                            ).show()
                            finish()
                        } else {
                            Log.w("FireBase", "sendPasswordResetEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                R.string.errortietForgotPasswordNotExit.getResourceStringAndroid(baseContext),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                binding.tilForgotPasswordUsuario.error = R.string.errortietForgotPasswordEmail.getResourceStringAndroid(baseContext)
            }
        }

        binding.tietForgotPasswordUsuario.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(p0: Editable?) {
                validateUsuario(p0.toString())
            }
        })

    }

    private fun validateUsuario(usuario: String) {
        if (viewModel.validateUsuario(usuario)) {
            binding.tilForgotPasswordUsuario.error = null
        } else {
            binding.tilForgotPasswordUsuario.error = R.string.errortietForgotPasswordEmail.getResourceStringAndroid(baseContext)
        }
    }
}