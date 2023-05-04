package com.jroslar.listafacturasv02.data.network.retrofit

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.jroslar.listafacturasv02.core.Constantes.Companion.URL_OBJECT_DETALLES
import com.jroslar.listafacturasv02.data.network.model.DetallesResponse
import retrofit2.Response
import retrofit2.http.GET

interface DetallesSmartSolarApiClient {
    @Mock
    @MockResponse(body = "{\n" +
            "  \"cau\": \"ES0021000000001994LJ1FA000\",\n" +
            "  \"estadoSolicitudAutoConsumidor\": \"No hemos recibido ninguna solicitud de autoconsumo\",\n" +
            "  \"tipoAutoConsumo\": \"Con excedentes y compensación Individual - Consumo\",\n" +
            "  \"compensacionExcedentes\": \"Precio PVPC\",\n" +
            "  \"potenciaInstalacion\": \"5kWp\",\n" +
            "}")
    @GET(URL_OBJECT_DETALLES)
    suspend fun getDetallesSmartSolar(): Response<DetallesResponse>
}