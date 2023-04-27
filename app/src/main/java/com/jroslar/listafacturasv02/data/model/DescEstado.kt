package com.jroslar.listafacturasv02.data.model

enum class DescEstado(val descEstado: String) {
    pedientedepago("Pendiente de pago"),
    pagada("Pagada"),
    anuladas("Anuladas"),
    cuotafija("Cuota Fija"),
    plandepago("Plan de pago")
}