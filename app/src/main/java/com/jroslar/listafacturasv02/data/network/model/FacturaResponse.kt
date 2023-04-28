package com.jroslar.listafacturasv02.data.network.model

import com.google.gson.annotations.SerializedName
import com.jroslar.listafacturasv02.data.model.FacturaModel

data class FacturaResponse (
    @SerializedName("descEstado") val descEstado: String,
    @SerializedName("importeOrdenacion") val importeOrdenacion: Float,
    @SerializedName("fecha") val fecha: String
)

fun FacturaResponse.toModelFactura(): FacturaModel = FacturaModel(descEstado, importeOrdenacion, fecha)