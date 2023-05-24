package com.jroslar.listafacturasv02.ui.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.Constantes.Companion.StateServer
import com.jroslar.listafacturasv02.data.network.FacturasService.Companion.stateServer
import com.jroslar.listafacturasv02.data.adapter.AdapterPracticas
import com.jroslar.listafacturasv02.databinding.ActivityDashboardBinding
import com.jroslar.listafacturasv02.ui.view.listafacturas.ListaFacturasActivity
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarActivity

class DashBoardActivity : AppCompatActivity(), AdapterPracticas.OnManagePractica, MenuProvider {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarPractica)

        //Quitar Titulo de la Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        addMenuProvider(this)

        intiAdapter()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.changeKtor -> {
                stateServer = StateServer.Ktor
                Toast.makeText(this, "Ktor", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.changeRetrofit -> {
                stateServer = StateServer.Retrofit
                Toast.makeText(this, "Retrofit", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.changeRetromock -> {
                stateServer = StateServer.Retromock
                Toast.makeText(this, "Retromock", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }

    private fun intiAdapter() {
        binding.rvListaPracticas.layoutManager = LinearLayoutManager(this)
        binding.rvListaPracticas.adapter = AdapterPracticas(this)
    }

    override fun onClickPractica(practica: AdapterPracticas.Practica) {
        if (practica.tipo == AdapterPracticas.PracticasName.PRACTICA1) {
            startActivity(Intent(this, ListaFacturasActivity::class.java))
        } else {
            startActivity(Intent(this, SmartSolarActivity::class.java))
        }
    }
}