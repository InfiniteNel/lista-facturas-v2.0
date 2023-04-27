package com.jroslar.listafacturasv02.data.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarDetallesFragment
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarEnergiaFragment
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarMiInstalacionFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
)

class SmartSolarSectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(context.resources.getString(TAB_TITLES[position])) {
            "Mi instalación" -> SmartSolarMiInstalacionFragment.newInstance()
            "Detalles" -> SmartSolarDetallesFragment.newInstance()
            "Energía" -> SmartSolarEnergiaFragment.newInstance()
            else -> SmartSolarMiInstalacionFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}