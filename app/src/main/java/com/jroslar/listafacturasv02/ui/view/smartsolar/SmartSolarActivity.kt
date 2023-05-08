package com.jroslar.listafacturasv02.ui.view.smartsolar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
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

        binding.toolbarDetalleBack.setOnClickListener {
            finish()
        }

        val smartSolarSectionsPagerAdapter = SmartSolarSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = smartSolarSectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}