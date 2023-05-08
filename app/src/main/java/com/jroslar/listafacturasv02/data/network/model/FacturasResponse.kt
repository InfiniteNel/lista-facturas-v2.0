package com.jroslar.listafacturasv02.data.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FacturasResponse (
    val numFacturas: Int,
    val facturas: List<FacturaResponse>
)