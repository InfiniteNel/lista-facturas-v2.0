package com.jroslar.listafacturasv02.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasv02.databinding.ActivitySignupBinding
import com.jroslar.listafacturasv02.ui.view.dashboard.DashBoardActivity
import com.jroslar.listafacturasv02.ui.viewmodel.login.SignupViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private var _viewModel: SignupViewModel? = null
    private val viewModel get() = _viewModel!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = getViewModel()

        auth = Firebase.auth

        binding.btSignupRegistrarse.setOnClickListener {
            if (viewModel.comprobarDatos()) {
                auth.createUserWithEmailAndPassword(viewModel.emailValue.value!!, viewModel.passwordValue.value!!)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("Firebase", "createUserWithEmail:success")
                            startActivity(Intent(this, DashBoardActivity::class.java))
                            finish()
                        } else {
                            Log.w("Firebase", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                R.string.errortietSignupDuplicate.getResourceStringAndroid(baseContext),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                validateError()
            }
        }

        binding.btSignupIniciarSesion.setOnClickListener {
            finish()
        }

        binding.tietSignupUsuario.addTextChangedListener(object:TextWatcher {
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

        binding.tietSignupContraseA.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePassword(p0.toString())
            }
        })

        binding.tietSignupRepetirContraseA.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(p0: Editable?) {
                validateRepeatPassword(p0.toString())
            }
        })
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