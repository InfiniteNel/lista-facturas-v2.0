package com.jroslar.listafacturasv02.domain

import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.model.FacturaModel

class GetFacturasFromApiUseCase constructor(
    private val repository: FacturasRepository
    ) {
    suspend operator fun invoke(): List<FacturaModel> {
        val facturas = repository.getAllFacturasFromApi()

        return if (facturas.isNotEmpty()) {
            repository.clearFacturas()
            repository.insertFacturas(facturas)
            facturas
        } else repository.getAllFacturasLocal()
    }
}