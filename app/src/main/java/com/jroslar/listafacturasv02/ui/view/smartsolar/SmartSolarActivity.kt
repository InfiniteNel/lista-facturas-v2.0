package com.jroslar.listafacturasv02.ui.view.smartsolar

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.data.adapter.SmartSolarSectionsPagerAdapter
import com.jroslar.listafacturasv02.databinding.ActivitySmartSolarBinding

class SmartSolarActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySmartSolarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySmartSolarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        /*supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_ios_24, theme)
        supportActionBar?.setHomeAsUpIndicator(upArrow)*/

        //binding.toolbar.contentInsetStartWithNavigation = 0

        binding.toolbarDetalleBack.setOnClickListener {
            finish()
        }

        val smartSolarSectionsPagerAdapter = SmartSolarSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = smartSolarSectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

    /*override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/
}