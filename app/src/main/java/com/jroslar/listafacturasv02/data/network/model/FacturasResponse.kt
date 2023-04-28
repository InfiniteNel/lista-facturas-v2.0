package com.jroslar.listafacturasv02.data.network.model

import com.google.gson.annotations.SerializedName

data class FacturasResponse (
    @SerializedName("numFacturas") val numFacturas: Int,
    @SerializedName("facturas") val facturas: List<FacturaResponse>
)