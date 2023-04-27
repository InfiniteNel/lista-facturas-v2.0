package com.jroslar.listafacturasv02.core

import co.infinum.retromock.Retromock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFacturas {
    private var INSTANCE_RETROFIT: Retrofit? = null
    private var INSTANCE_RETROMOCK: Retromock? = null

    fun getRetrofit(): Retrofit {
        var instance = INSTANCE_RETROFIT

        if (instance == null) {
            instance = Retrofit.Builder()
                //.baseUrl("https://viewnextandroid.mocklab.io/")
                .baseUrl("https://viewnextandroid.wiremockapi.cloud/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            INSTANCE_RETROFIT = instance
        }
        return instance!!
    }

    fun getRetromock(): Retromock {
        var instance = INSTANCE_RETROMOCK

        if (instance == null) {
            instance = Retromock.Builder()
                .retrofit(getRetrofit())
                .build()
            INSTANCE_RETROMOCK = instance
        }
        return instance!!
    }
}