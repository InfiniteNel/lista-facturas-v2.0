package com.jroslar.listafacturasv02.domain

import android.content.Context
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.model.FacturaModel

class GetFacturasLocalUseCase constructor(
    private val repository: FacturasRepository
) {
    suspend operator fun invoke(): List<FacturaModel> {
        val facturas = repository.getAllFacturasLocal()

        return if (facturas.isNotEmpty()) facturas
            else emptyList()
    }
}