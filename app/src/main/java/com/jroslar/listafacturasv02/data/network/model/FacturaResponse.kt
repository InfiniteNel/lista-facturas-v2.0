package com.jroslar.listafacturasv02.data.network.model

import com.google.gson.annotations.SerializedName
import com.jroslar.listafacturasv02.data.model.FacturaModel
import kotlinx.serialization.Serializable

@Serializable
data class FacturaResponse (
    val descEstado: String,
    val importeOrdenacion: Float,
    val fecha: String
)

fun FacturaResponse.toModelFactura(): FacturaModel = FacturaModel(descEstado, importeOrdenacion, fecha)