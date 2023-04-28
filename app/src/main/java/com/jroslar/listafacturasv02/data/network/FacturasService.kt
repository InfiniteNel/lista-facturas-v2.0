package com.jroslar.listafacturasv02.data.network

import co.infinum.retromock.Retromock
import com.jroslar.listafacturasv02.data.model.FacturasModel
import com.jroslar.listafacturasv02.data.network.model.FacturasResponse
import retrofit2.Retrofit

class FacturasService constructor(private val retrofit: Retrofit, private val retromock: Retromock) {

    suspend fun getFacturas(serverOn: Boolean): FacturasResponse {
        return if (serverOn) {
            val response = retrofit.create(FacturasApiClient::class.java).getAllFacturas()
            response.body()?: FacturasResponse(0, emptyList())
        } else {
            val response = retromock.create(FacturasApiClient::class.java).getAllFacturas()
            response.body()?: FacturasResponse(0, emptyList())
        }
    }
}