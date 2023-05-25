package com.jroslar.listafacturasv02.ui

import android.app.Application
import com.jroslar.listafacturasv02.core.Prefs
import com.jroslar.listafacturasv02.di.dataModule
import com.jroslar.listafacturasv02.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(dataModule, viewModelModule)
        }
    }
}