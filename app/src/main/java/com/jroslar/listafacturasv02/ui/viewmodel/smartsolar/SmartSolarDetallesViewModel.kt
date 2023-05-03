package com.jroslar.listafacturasv02.ui.viewmodel.smartsolar

import androidx.lifecycle.*
import com.jroslar.listafacturasv02.data.model.DetallesModel
import com.jroslar.listafacturasv02.data.model.FacturaModel
import com.jroslar.listafacturasv02.domain.GetDetallesFromApiUseCase
import com.jroslar.listafacturasv02.domain.GetFacturasLocalUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SmartSolarDetallesViewModel : ViewModel() {
    var _state: MutableLiveData<DetallesModel> = MutableLiveData()

    private object Injection: KoinComponent {
        val getDetallesFromApiUseCase by inject<GetDetallesFromApiUseCase>()
    }
    private val getDetallesFromApiUseCase = Injection.getDetallesFromApiUseCase

    init {
        viewModelScope.launch {
            _state.value = getDetallesFromApiUseCase()!!
        }
    }
}