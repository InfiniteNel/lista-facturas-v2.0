package com.jroslar.listafacturasv02.ui.view.listafacturas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.databinding.ActivityListaFacturasBinding

class ListaFacturasActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityListaFacturasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val topLevelDestination: MutableSet<Int> = HashSet()
        topLevelDestination.add(R.id.ListaFacturasFragment)
        topLevelDestination.add(R.id.filtrarFacturasFragment)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration =
            AppBarConfiguration.Builder(topLevelDestination).build()
        setupActionBarWithNavController(navController, appBarConfiguration)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.filtrarFacturasFragment -> {
                    binding.toolbarDetalleBack.visibility = View.INVISIBLE
                }
                R.id.ListaFacturasFragment -> {
                    binding.toolbarDetalleBack.visibility = View.VISIBLE
                }
                else -> {
                    //
                }
            }
        }

        binding.toolbarDetalleBack.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}