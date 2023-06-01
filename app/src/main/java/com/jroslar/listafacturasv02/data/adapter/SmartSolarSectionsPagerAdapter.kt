package com.jroslar.listafacturasv02.data.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.extensions.getResourceStringAndroid
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarDetallesFragment
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarEnergiaFragment
import com.jroslar.listafacturasv02.ui.view.smartsolar.SmartSolarMiInstalacionFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
)

@Suppress("DEPRECATION")
class SmartSolarSectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(TAB_TITLES[position]) {
            R.string.tab_text_1 -> SmartSolarMiInstalacionFragment.newInstance()
            R.string.tab_text_3 -> SmartSolarDetallesFragment.newInstance()
            R.string.tab_text_2 -> SmartSolarEnergiaFragment.newInstance()
            else -> SmartSolarMiInstalacionFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position].getResourceStringAndroid(context)
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}