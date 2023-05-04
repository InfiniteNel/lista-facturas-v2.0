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
import com.jroslar.listafacturasv02.core.Constantes.Companion.MONEDA_VALUE
import com.jroslar.listafacturasv02.core.Extensions.Companion.castStringToDate
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

        viewModel.getList()

        val bundle = arguments
        if (bundle!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val maxImporte = floor(bundle.getFloat(MAX_IMPORTE).toDouble()).toInt() + 1
            binding.sbImporte.max = maxImporte
            binding.sbImporte.progress = maxImporte
            binding.tvMaxImporte.text = "$maxImporte$MONEDA_VALUE"

            binding.sbImporte.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    binding.tvRankImporte.text = "${binding.sbImporte.min}$MONEDA_VALUE  -  ${binding.sbImporte.progress}$MONEDA_VALUE"
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                    //
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                    //
                }
            })
        }

        binding.btFechaDesde.setOnClickListener {
            showDatePicker(binding.btFechaDesde)
        }

        binding.btFechaHasta.setOnClickListener {
            showDatePicker(binding.btFechaHasta)
        }

        binding.btEliminarFiltro.setOnClickListener {
            eliminarFiltros()
        }

        binding.btAplicarFiltro.setOnClickListener{
            comprobarCheckBoxs()
            comprobarFechas()
            viewModel.filterListByImporte(binding.sbImporte.progress)

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
        var text = binding.btFechaDesde.text
        val regex = "\\d{1,2} [A-Z a-z]{3} \\d{4}".toRegex()
        if (text != null && regex.matches(text)) viewModel.filterlistByFechaDesde(text.toString())
        text = binding.btFechaHasta.text
        if (text != null && regex.matches(text)) viewModel.filterlistByFechaHasta(text.toString())
    }

    private fun comprobarCheckBoxs() {
        var checks = mutableListOf<String>()
        if (binding.chAnuladas.isChecked) checks.add(DescEstado.Anuladas.descEstado)
        if (binding.chCuotaFija.isChecked) checks.add(DescEstado.CuotaFija.descEstado)
        if (binding.chPedientesDePago.isChecked) checks.add(DescEstado.PedienteDePago.descEstado)
        if (binding.chPagado.isChecked) checks.add(DescEstado.Pagada.descEstado)
        if (binding.chPlanDePago.isChecked) checks.add(DescEstado.PlanDePago.descEstado)

        viewModel.filterListByCheckBox(checks)
    }

    private fun eliminarFiltros() {
        binding.chAnuladas.isChecked = false
        binding.chCuotaFija.isChecked = false
        binding.chPedientesDePago.isChecked = false
        binding.chPagado.isChecked = false
        binding.chPlanDePago.isChecked = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.sbImporte.progress = binding.sbImporte.max
        }

        binding.btFechaDesde.text = null
        binding.btFechaHasta.text = null
    }
}