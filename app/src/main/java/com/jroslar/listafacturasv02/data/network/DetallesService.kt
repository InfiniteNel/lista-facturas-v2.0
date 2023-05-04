package com.jroslar.listafacturasv02.data.network

import co.infinum.retromock.Retromock
import com.jroslar.listafacturasv02.core.Constantes.Companion.StateServer
import com.jroslar.listafacturasv02.data.model.DetallesModel
import com.jroslar.listafacturasv02.data.network.model.toModelDetalles
import com.jroslar.listafacturasv02.data.network.retrofit.DetallesSmartSolarApiClient
import retrofit2.Retrofit

class DetallesService constructor(private val retrofit: Retrofit, private val retromock: Retromock) {
    companion object {
        var stateServer: StateServer = StateServer.Retrofit
    }
    suspend fun getDetalles(): DetallesModel {
        return when(stateServer) {
            StateServer.Retrofit -> {
                val response = retrofit.create(DetallesSmartSolarApiClient::class.java).getDetallesSmartSolar()
                return response.body()?.toModelDetalles() ?: noData()
            }
            StateServer.Retromock -> {
                val response = retromock.create(DetallesSmartSolarApiClient::class.java).getDetallesSmartSolar()
                return response.body()?.toModelDetalles() ?: noData()
            }
            else -> {
                noData()
            }
        }
    }

    private fun noData(): DetallesModel {
        return DetallesModel(
            "undefined",
            "undefined",
            "undefined",
            "undefined",
            "undefined")
    }
}