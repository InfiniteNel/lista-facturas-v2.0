package com.jroslar.listafacturasv02.data

import com.jroslar.listafacturasv02.data.model.DetallesModel
import com.jroslar.listafacturasv02.data.network.DetallesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetallesRepository constructor(private val facturasService: DetallesService) {
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO

    suspend fun getDetallesFromApi(): DetallesModel {
        return withContext(dispatcherIO) {
            //facturasService.getDetalles()
            DetallesModel(
                "ES0021000000001994LJ1FA000",
                "No hemos recibido ninguna solicitud de autoconsumo",
                "Con excedentes y compensación Individual - Consumo",
                "Precio PVPC",
                "5kWp"
            )
        }
    }
}