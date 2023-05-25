package com.jroslar.listafacturasv02.di

import com.jroslar.listafacturasv02.ui.viewmodel.listafacturas.FiltrarFacturasViewModel
import com.jroslar.listafacturasv02.ui.viewmodel.listafacturas.ListaFacturasViewModel
import com.jroslar.listafacturasv02.ui.viewmodel.login.LoginViewModel
import com.jroslar.listafacturasv02.ui.viewmodel.smartsolar.SmartSolarDetallesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::FiltrarFacturasViewModel)
    viewModelOf(::ListaFacturasViewModel)
    viewModelOf(::SmartSolarDetallesViewModel)
    viewModelOf(::LoginViewModel)
}