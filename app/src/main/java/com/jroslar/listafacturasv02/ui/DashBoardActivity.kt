package com.jroslar.listafacturasv02.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.data.adapter.AdapterPracticas
import com.jroslar.listafacturasv02.databinding.ActivityDashboardBinding
import com.jroslar.listafacturasv02.ui.view.listafacturas.ListaFacturasActivity
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarActivity

class DashBoardActivity : AppCompatActivity(), AdapterPracticas.OnManagePractica {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarPractica)

        //Quitar Titulo de la Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        intiAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.changeKtor -> {

                true
            }
            R.id.changeRetrofit -> {

                true
            }
            R.id.changeRetromock -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun intiAdapter() {
        binding.rvListaPracticas.layoutManager = LinearLayoutManager(this)
        binding.rvListaPracticas.adapter = AdapterPracticas(this)
    }

    override fun onClickPractica(practica: AdapterPracticas.Practica) {
        when (practica.tipo) {
            AdapterPracticas.PracticasName.PRACTICA1 -> startActivity(Intent(this, ListaFacturasActivity::class.java))
            AdapterPracticas.PracticasName.PRACTICA2 -> startActivity(Intent(this, SmartSolarActivity::class.java))
        }
    }
}