package com.jroslar.listafacturasv02.ui.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jroslar.listafacturasv02.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLoginEntrar.setOnClickListener {
            Toast.makeText(this, "Entrar", Toast.LENGTH_SHORT).show()
        }
    }
}