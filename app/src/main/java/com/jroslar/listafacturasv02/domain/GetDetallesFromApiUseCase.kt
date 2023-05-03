package com.jroslar.listafacturasv02.domain

import com.jroslar.listafacturasv02.data.DetallesRepository
import com.jroslar.listafacturasv02.data.model.DetallesModel

class GetDetallesFromApiUseCase constructor(
    private val repository: DetallesRepository
) {
    suspend operator fun invoke(): DetallesModel {
        return repository.getDetallesFromApi()
    }
}