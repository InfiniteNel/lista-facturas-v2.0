package com.jroslar.listafacturasv02.data.network

import com.jroslar.listafacturasv02.core.RetrofitFacturas
import com.jroslar.listafacturasv02.data.model.FacturasModel

class FacturasService {
    private val retrofit = RetrofitFacturas.getRetrofit()
    private val retromock = RetrofitFacturas.getRetromock()

    suspend fun getFacturas(serverOn: Boolean): FacturasModel {
        return if (serverOn) {
            val response = retrofit.create(FacturasApiClient::class.java).getAllFacturas()
            response.body()?: FacturasModel(0, emptyList())
        } else {
            val response = retromock.create(FacturasApiClient::class.java).getAllFacturas()
            response.body()?: FacturasModel(0, emptyList())
        }
    }
}