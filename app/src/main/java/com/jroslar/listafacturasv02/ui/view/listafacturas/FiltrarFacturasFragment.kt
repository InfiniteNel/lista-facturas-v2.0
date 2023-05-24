package com.jroslar.listafacturasv02.ui.view.listafacturas

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.Constantes.Companion.DescEstado
import com.jroslar.listafacturasv02.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasv02.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasv02.data.model.FacturasModel
import com.jroslar.listafacturasv02.databinding.FragmentFiltrarFacturasBinding
import com.jroslar.listafacturasv02.ui.view.listafacturas.ListaFacturasFragment.Companion.DATA_FILTER
import com.jroslar.listafacturasv02.ui.view.listafacturas.ListaFacturasFragment.Companion.MAX_IMPORTE
import com.jroslar.listafacturasv02.ui.viewmodel.listafacturas.FiltrarFacturasViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.floor


class FiltrarFacturasFragment : Fragment(), MenuProvider {

    private var _binding: FragmentFiltrarFacturasBinding? = null
    private val binding get() = _binding!!
    private var _viewModel: FiltrarFacturasViewModel? = null
    private val viewModel get() = _viewModel!!
    private var _moneda: String? = null
    private val moneda get() = _moneda!!

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_filtrar, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.cerrarFiltrarFacturas -> {
                findNavController().navigateUp()
                true
            }
            else -> true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFiltrarFacturasBinding.inflate(inflater, container, false)
        return binding.root

    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        _viewModel = getViewModel()
        _moneda = R.string.monedaValue.getResourceStringAndroid(requireContext())

        viewModel.getList()

        viewModel._valueFiltroImporte.observe(viewLifecycleOwner) {
            binding.sbImporteFiltrarFactura.progress = it
            binding.tvRankImporteFiltrarFactura.text = "${binding.sbImporteFiltrarFactura.min}$moneda  -  $it$moneda"
        }

        viewModel._valueFiltroFechaDesde.observe(viewLifecycleOwner) {
            binding.btFechaDesdeFiltrarFactura.text = it
        }

        viewModel._valueFiltroFechahasta.observe(viewLifecycleOwner) {
            binding.btFechaHastaFiltrarFactura.text = it
        }

        val bundle = arguments
        if (bundle != null) {
            val maxImporte = floor(bundle.getFloat(MAX_IMPORTE).toDouble()).toInt() + 1
            binding.sbImporteFiltrarFactura.max = maxImporte

            if (viewModel._valueFiltroImporte.value == null) {
                viewModel._valueFiltroImporte.value = maxImporte
            }

            binding.tvMaxImporteFiltrarFactura.text = "$maxImporte$moneda"
            binding.tvMinImporteFiltrarFactura.text = "0$moneda"

            binding.sbImporteFiltrarFactura.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {

                    viewModel._valueFiltroImporte.value = progress
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                    //
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                    //
                }
            })
        }

        binding.btFechaDesdeFiltrarFactura.setOnClickListener {
            showDatePicker(viewModel._valueFiltroFechaDesde)
        }

        binding.btFechaHastaFiltrarFactura.setOnClickListener {
            showDatePicker(viewModel._valueFiltroFechahasta)
        }

        binding.btEliminarFiltroFiltrarFactura.setOnClickListener {
            eliminarFiltros()
        }

        binding.btAplicarFiltroFiltrarFactura.setOnClickListener{
            comprobarCheckBoxs()
            viewModel.comprobarFechas()
            viewModel.filterListByImporte()

            val bundleToListaFactura = Bundle()
            bundleToListaFactura.putParcelable(DATA_FILTER, FacturasModel(viewModel._state.value!!.size, viewModel._state.value!!))
            setFragmentResult(DATA_FILTER, bundleToListaFactura)
            findNavController().navigateUp()
        }
    }

    private fun showDatePicker(_valueFiltroFecha: MutableLiveData<String>) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpdFecha = DatePickerDialog(requireContext(), { _, yearDatePicker, monthOfYearDatePicker, dayOfMonthDatePicker ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val newdf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale(Locale.getDefault().displayLanguage))
                _valueFiltroFecha.value = "$dayOfMonthDatePicker/${monthOfYearDatePicker+1}/$yearDatePicker".castStringToDate().format(newdf)
            }
        }, year, month, day)
        dpdFecha.datePicker.maxDate = Date().time
        dpdFecha.show()
    }

    private fun comprobarCheckBoxs() {
        val checks = mutableListOf<String>()
        if (binding.chAnuladasFiltrarFactura.isChecked) {
            checks.add(DescEstado.Anuladas.descEstado)
        }
        if (binding.chCuotaFijaFiltrarFactura.isChecked) {
            checks.add(DescEstado.CuotaFija.descEstado)
        }
        if (binding.chPedientesDePagoFiltrarFactura.isChecked) {
            checks.add(DescEstado.PedienteDePago.descEstado)
        }
        if (binding.chPagadoFiltrarFactura.isChecked) {
            checks.add(DescEstado.Pagada.descEstado)
        }
        if (binding.chPlanDePagoFiltrarFactura.isChecked) {
            checks.add(DescEstado.PlanDePago.descEstado)
        }

        viewModel.filterListByCheckBox(checks)
    }

    private fun eliminarFiltros() {
        binding.chAnuladasFiltrarFactura.isChecked = false
        binding.chCuotaFijaFiltrarFactura.isChecked = false
        binding.chPedientesDePagoFiltrarFactura.isChecked = false
        binding.chPagadoFiltrarFactura.isChecked = false
        binding.chPlanDePagoFiltrarFactura.isChecked = false

        viewModel._valueFiltroImporte.value = binding.sbImporteFiltrarFactura.max

        viewModel._valueFiltroFechahasta.value = null
        viewModel._valueFiltroFechaDesde.value = null
    }
}