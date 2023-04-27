package com.jroslar.listafacturasv02.ui.viewmodel.listafacturas

import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv02.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.model.FacturaModel
import com.jroslar.listafacturasv02.domain.GetFacturasLocalUseCase
import kotlinx.coroutines.launch

class FiltrarFacturasViewModel(val context: Context): ViewModel() {
    var _state: MutableLiveData<List<FacturaModel>> = MutableLiveData()

    private val getFacturasLocalUseCase = GetFacturasLocalUseCase()
    private val repository = FacturasRepository()

    fun getList() {
        viewModelScope.launch {
            val data: List<FacturaModel> = getFacturasLocalUseCase(context, repository)
            if (!data.isNullOrEmpty()) {
                _state.value = data
            } else {
                _state.value = emptyList()
            }
        }
    }

    fun filterListByCheckBox(value: List<String>) {
        if (!value.isNullOrEmpty()) {
            _state.value = _state.value?.filter { value.contains(it.descEstado) }
        }
    }

    fun filterListByImporte(value: Int) {
        _state.value = _state.value?.filter { it.importeOrdenacion < value }
    }

    fun filterlistByFechaDesde(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isBefore(it.fecha.castStringToDate())}
        }
    }

    fun filterlistByFechaHasta(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isAfter(it.fecha.castStringToDate())}
        }
    }
}

class FiltrarFacturasViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FiltrarFacturasViewModel(context) as T
    }
}