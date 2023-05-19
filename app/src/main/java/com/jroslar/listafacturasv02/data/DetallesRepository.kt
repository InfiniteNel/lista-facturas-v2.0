package com.jroslar.listafacturasv02.data

import com.jroslar.listafacturasv02.data.model.DetallesModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DetallesRepository constructor(
    private val dispatcherIO: CoroutineDispatcher
    ) {
    suspend fun getDetallesFromApi(): DetallesModel {
        return withContext(dispatcherIO) {
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