package com.jroslar.listafacturasv02.domain

import android.content.Context
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.model.FacturaModel

class GetFacturasLocalUseCase {
    suspend operator fun invoke(context: Context, repository: FacturasRepository): List<FacturaModel> {
        val facturas = repository.getAllFacturasLocal(context)

        return if (facturas.isNotEmpty()) facturas
            else emptyList()
    }
}