package com.jroslar.listafacturasv02.ui.view.login

import android.content.Intent
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
import com.jroslar.listafacturasv02.databinding.ActivityLoginBinding
import com.jroslar.listafacturasv02.ui.App
import com.jroslar.listafacturasv02.ui.view.dashboard.DashBoardActivity
import com.jroslar.listafacturasv02.ui.viewmodel.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var _viewModel: LoginViewModel? = null
    private val viewModel get() = _viewModel!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        _viewModel = getViewModel()


        binding.btLoginEntrar.setOnClickListener {
            if (viewModel.validateData()) {
                auth.signInWithEmailAndPassword(viewModel.emailValue.value!!,
                    viewModel.passwordValue.value!!
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("FireBase", "signInWithEmail:success")
                            dataSaved()
                            startActivity(Intent(this, DashBoardActivity::class.java))
                        } else {
                            Log.w("FireBase", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                R.string.errortietLoginNotExit.getResourceStringAndroid(baseContext),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                validateError()
            }
        }

        binding.chLoginDatosOlvidados.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btLoginRegistrarte.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.tietLoginUsuario.addTextChangedListener(object: TextWatcher {
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

        binding.tietLoginContraseA.addTextChangedListener(object: TextWatcher {
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
    }

    override fun onStart() {
        super.onStart()

        val email = App.prefs.email
        val password = App.prefs.password

        if (email.isNotEmpty() && password.isNotEmpty()) {
            binding.chLoginRecordarContraseA.isChecked = true
            binding.tietLoginUsuario.setText(email)
            binding.tietLoginContraseA.setText(password)
        } else {
            binding.tietLoginUsuario.text = null
            binding.tietLoginContraseA.text = null
            binding.tilLoginUsuario.error = null
            binding.tilLoginContraseA.error = null
        }
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
        if (!binding.tilLoginUsuario.isErrorEnabled) {
            binding.tilLoginUsuario.error = R.string.errortietLoginUsuario.getResourceStringAndroid(baseContext)
        }
        if (!binding.tilLoginContraseA.isErrorEnabled) {
            setErrorContrasea()
        }
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