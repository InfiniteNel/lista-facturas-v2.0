package com.jroslar.listafacturasv02.ui.viewmodel.listafacturas

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv02.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasv02.data.model.FacturaModel
import com.jroslar.listafacturasv02.domain.GetFacturasLocalUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FiltrarFacturasViewModel : ViewModel() {
    var _state: MutableLiveData<List<FacturaModel>> = MutableLiveData()
    var _valueFiltroImporte: MutableLiveData<Int> = MutableLiveData()
    var _valueFiltroFechaDesde: MutableLiveData<String> = MutableLiveData()
    var _valueFiltroFechahasta: MutableLiveData<String> = MutableLiveData()

    private object Injection: KoinComponent {
        val getFacturasLocalUseCase by inject<GetFacturasLocalUseCase>()
    }
    private val getFacturasLocalUseCase = Injection.getFacturasLocalUseCase

    fun getList() {
        viewModelScope.launch {
            val data: List<FacturaModel> = getFacturasLocalUseCase()
            if (data.isNullOrEmpty()) {
                _state.value = emptyList()
            } else {
                _state.value = data
            }
        }
    }

    fun filterListByCheckBox(value: List<String>) {
        if (!value.isNullOrEmpty()) { _state.value = _state.value?.filter { value.contains(it.descEstado) } }
    }

    fun filterListByImporte() {
        _state.value = _state.value?.filter { it.importeOrdenacion < _valueFiltroImporte.value!! }
    }

    private fun filterlistByFechaDesde(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isBefore(it.fecha.castStringToDate())}
        }
    }

    private fun filterlistByFechaHasta(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isAfter(it.fecha.castStringToDate())}
        }
    }

    fun comprobarFechas() {
        var text = _valueFiltroFechaDesde.value
        val regex = "\\d{1,2} [A-Z a-z]{3} \\d{4}".toRegex()
        if (text != null && regex.matches(text)) {
            filterlistByFechaDesde(text.toString())
        }
        text = _valueFiltroFechahasta.value
        if (text != null && regex.matches(text)) {
            filterlistByFechaHasta(text.toString())
        }
    }
}