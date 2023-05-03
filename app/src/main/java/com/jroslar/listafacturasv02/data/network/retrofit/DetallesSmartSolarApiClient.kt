package com.jroslar.listafacturasv02.data.network.retrofit

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.jroslar.listafacturasv02.data.network.model.DetallesResponse
import retrofit2.Response

interface DetallesSmartSolarApiClient {
    @Mock
    @MockResponse(body = "{\n" +
            "  \"cau\": \"ES0021000000001994LJ1FA000\",\n" +
            "  \"estadoSolicitudAutoConsumidor\": \"No hemos recibido ninguna solicitud de autoconsumo\",\n" +
            "  \"tipoAutoConsumo\": \"Con excedentes y compensación Individual - Consumo\",\n" +
            "  \"compensacionExcedentes\": \"Precio PVPC\",\n" +
            "  \"potenciaInstalacion\": \"5kWp\",\n" +
            "}")
    suspend fun getDetallesSmartSolar(): Response<DetallesResponse>
}