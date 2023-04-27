package com.jroslar.listafacturasv02.domain

import android.content.Context
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.model.FacturaModel

class GetFacturasFromApiUseCase {
    suspend operator fun invoke(context: Context, repository: FacturasRepository, severOn: Boolean): List<FacturaModel> {
        val facturas = repository.getAllFacturasFromApi(severOn)

        return if (facturas.isNotEmpty()) {
            repository.clearFacturas(context)
            repository.insertFacturas(context, facturas)
            facturas
        } else repository.getAllFacturasLocal(context)
    }
}