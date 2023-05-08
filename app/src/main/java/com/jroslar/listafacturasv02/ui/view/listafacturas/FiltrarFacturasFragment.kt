package com.jroslar.listafacturasv02.ui.view.listafacturas

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.SeekBar
import androidx.fragment.app.setFragmentResult
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


class FiltrarFacturasFragment : Fragment() {

    private var _binding: FragmentFiltrarFacturasBinding? = null
    private val binding get() = _binding!!
    private var _viewModel: FiltrarFacturasViewModel? = null
    private val viewModel get() = _viewModel!!
    private var _moneda: String? = null
    private val moneda get() = _moneda!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filtrar, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrarFiltrarFacturas -> {
                findNavController().navigateUp()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFiltrarFacturasBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = getViewModel()
        _moneda = R.string.monedaValue.getResourceStringAndroid(requireContext())

        viewModel.getList()

        val bundle = arguments
        if (bundle!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val maxImporte = floor(bundle.getFloat(MAX_IMPORTE).toDouble()).toInt() + 1
            binding.sbImporteFiltrarFactura.max = maxImporte
            binding.sbImporteFiltrarFactura.progress = maxImporte
            binding.tvRankImporteFiltrarFactura.text = "${binding.sbImporteFiltrarFactura.min}$moneda  -  ${binding.sbImporteFiltrarFactura.progress}$moneda"
            binding.tvMaxImporteFiltrarFactura.text = "$maxImporte$moneda"
            binding.tvMinImporteFiltrarFactura.text = "0$moneda"

            binding.sbImporteFiltrarFactura.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    binding.tvRankImporteFiltrarFactura.text = "${binding.sbImporteFiltrarFactura.min}$moneda  -  ${binding.sbImporteFiltrarFactura.progress}$moneda"
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
            showDatePicker(binding.btFechaDesdeFiltrarFactura)
        }

        binding.btFechaHastaFiltrarFactura.setOnClickListener {
            showDatePicker(binding.btFechaHastaFiltrarFactura)
        }

        binding.btEliminarFiltroFiltrarFactura.setOnClickListener {
            eliminarFiltros()
        }

        binding.btAplicarFiltroFiltrarFactura.setOnClickListener{
            comprobarCheckBoxs()
            comprobarFechas()
            viewModel.filterListByImporte(binding.sbImporteFiltrarFactura.progress)

            var bundle = Bundle()
            bundle.putParcelable(DATA_FILTER, FacturasModel(viewModel._state.value!!.size, viewModel._state.value!!))
            setFragmentResult(DATA_FILTER, bundle)
            findNavController().navigateUp()
        }
    }

    private fun showDatePicker(button: Button) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpdFecha = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val newdf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
                button.text = "$dayOfMonth/${monthOfYear+1}/$year".castStringToDate().format(newdf)
            }
        }, year, month, day)
        dpdFecha.datePicker.maxDate = Date().time
        dpdFecha.show()
    }

    private fun comprobarFechas() {
        var text = binding.btFechaDesdeFiltrarFactura.text
        val regex = "\\d{1,2} [A-Z a-z]{3} \\d{4}".toRegex()
        if (text != null && regex.matches(text)) {
            viewModel.filterlistByFechaDesde(text.toString())
        }
        text = binding.btFechaHastaFiltrarFactura.text
        if (text != null && regex.matches(text)) {
            viewModel.filterlistByFechaHasta(text.toString())
        }
    }

    private fun comprobarCheckBoxs() {
        var checks = mutableListOf<String>()
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.sbImporteFiltrarFactura.progress = binding.sbImporteFiltrarFactura.max
        }

        binding.btFechaDesdeFiltrarFactura.text = null
        binding.btFechaHastaFiltrarFactura.text = null
    }
}