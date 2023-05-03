package com.jroslar.listafacturasv02.data.network.model

import com.jroslar.listafacturasv02.data.model.DetallesModel

data class DetallesResponse(
    val cau:String,
    val estadoSolicitudAutoConsumidor:String,
    val tipoAutoConsumo:String,
    val compensacionExcedentes:String,
    val potenciaInstalacion:String
)

fun DetallesResponse.toModelDetalles(): DetallesModel = DetallesModel(cau, estadoSolicitudAutoConsumidor, tipoAutoConsumo, compensacionExcedentes, potenciaInstalacion)
