package com.jroslar.listafacturasv02.ui.view.listafacturas

import android.os.Bundle
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
        topLevelDestination.add(R.id.filtrarFacturasFragment)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration =
            AppBarConfiguration.Builder(topLevelDestination).build()
        setupActionBarWithNavController(navController, appBarConfiguration)

        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_ios_24, theme)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.navigationIcon = upArrow
            when(destination.id) {
                R.id.filtrarFacturasFragment -> {
                    binding.toolbar.navigationIcon = null
                }
                R.id.ListaFacturasFragment -> {
                    binding.toolbar.navigationIcon = upArrow
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}