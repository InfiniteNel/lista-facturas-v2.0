package com.jroslar.listafacturasv02.data.network

import co.infinum.retromock.Retromock
import com.jroslar.listafacturasv02.data.model.DetallesModel
import com.jroslar.listafacturasv02.data.network.model.toModelDetalles
import com.jroslar.listafacturasv02.data.network.retrofit.DetallesSmartSolarApiClient
import retrofit2.Retrofit

class DetallesService constructor(private val retrofit: Retrofit, private val retromock: Retromock) {
    suspend fun getDetalles(): DetallesModel {
        val response = retromock.create(DetallesSmartSolarApiClient::class.java).getDetallesSmartSolar()
        return response.body()?.toModelDetalles() ?: DetallesModel(
            "undefined",
            "undefined",
            "undefined",
            "undefined",
            "undefined")
    }
}