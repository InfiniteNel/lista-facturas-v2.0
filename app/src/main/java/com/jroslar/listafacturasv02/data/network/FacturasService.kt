package com.jroslar.listafacturasv02.data.network

import android.util.Log
import co.infinum.retromock.Retromock
import com.jroslar.listafacturasv02.data.network.model.FacturasResponse
import com.jroslar.listafacturasv02.data.network.retrofit.FacturasApiClient
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import retrofit2.Retrofit

class FacturasService constructor(private val retrofit: Retrofit, private val retromock: Retromock, private val client: HttpClient) {
    private var state = StatesServer.Ktor

    suspend fun getFacturas(): FacturasResponse {
        return when(state) {
            StatesServer.Retrofit -> {
                val response = retrofit.create(FacturasApiClient::class.java).getAllFacturas()
                response.body()?: FacturasResponse(0, emptyList())
            }
            StatesServer.Ktor -> {
                try {
                    client.get { url("https://viewnextandroid.wiremockapi.cloud/facturas") }
                } catch (e: RedirectResponseException) {
                    FacturasResponse(0, emptyList())
                } catch (e: ClientRequestException) {
                    FacturasResponse(0, emptyList())
                } catch (e: ServerResponseException) {
                    FacturasResponse(0, emptyList())
                } catch (e: Exception) {
                    FacturasResponse(0, emptyList())
                }
            }
            else -> {
                val response = retromock.create(FacturasApiClient::class.java).getAllFacturas()
                response.body()?: FacturasResponse(0, emptyList())
            }
        }
    }

    enum class StatesServer {
        Retrofit,
        Retromock,
        Ktor
    }
}