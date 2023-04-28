package com.jroslar.listafacturasv02.data.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FacturasResponse (
    @SerializedName("numFacturas") val numFacturas: Int,
    @SerializedName("facturas") val facturas: List<FacturaResponse>
)