package com.jroslar.listafacturasv02.ui.viewmodel.listafacturas

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv02.data.model.FacturaModel
import com.jroslar.listafacturasv02.domain.GetFacturasFromApiUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListaFacturasViewModel constructor(private val context: Context): ViewModel() {
    var _data: MutableLiveData<List<FacturaModel>> = MutableLiveData()
    var _state: MutableLiveData<ListaFacturasResult> = MutableLiveData()
    var _maxValueImporte: MutableLiveData<Float> = MutableLiveData()

    private object Injection: KoinComponent {
        val getFacturasUseCase by inject<GetFacturasFromApiUseCase>()
    }
    private val getFacturasUseCase = Injection.getFacturasUseCase

    init {
        loadingData()
    }

    fun loadingData() {
        viewModelScope.launch {
            _state.postValue(ListaFacturasResult.LOADING)
            _data.postValue(emptyList())
            if (checkForInternet(context)) {
                val data: List<FacturaModel> = getFacturasUseCase()
                if (!data.isNullOrEmpty()) {
                    _data.postValue(data)
                    _state.postValue(ListaFacturasResult.DATA)
                    _maxValueImporte.postValue(data.sortedBy { it.importeOrdenacion }[data.size - 1].importeOrdenacion)
                } else {
                    _state.postValue(ListaFacturasResult.API_NO_DATA)
                    _maxValueImporte.postValue(0F)
                }
            } else _state.postValue(ListaFacturasResult.NO_DATA)
        }
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun getList(data: List<FacturaModel>) {
        if (!data.isNullOrEmpty()) {
            _data.postValue(data)
            _state.postValue(ListaFacturasResult.DATA)
        } else {
            _data.postValue(emptyList())
            _state.postValue(ListaFacturasResult.NO_DATA)
        }
    }

    enum class ListaFacturasResult {
        LOADING,
        API_NO_DATA,
        NO_DATA,
        DATA
    }
}