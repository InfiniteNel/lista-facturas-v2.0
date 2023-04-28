package com.jroslar.listafacturasv02.data.model


import com.jroslar.listafacturasv02.data.database.model.FacturaEntity

data class FacturaModel(
    val descEstado: String,
    val importeOrdenacion: Float,
    val fecha: String
)

fun FacturaModel.toFacturaEntity(): FacturaEntity = FacturaEntity(null, descEstado, importeOrdenacion, fecha)
