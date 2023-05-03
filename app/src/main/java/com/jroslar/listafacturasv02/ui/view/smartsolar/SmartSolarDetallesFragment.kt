package com.jroslar.listafacturasv02.ui.view.smartsolar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.databinding.FragmentSmartSolarDetallesBinding
import com.jroslar.listafacturasv02.ui.viewmodel.listafacturas.ListaFacturasViewModel
import com.jroslar.listafacturasv02.ui.viewmodel.smartsolar.SmartSolarDetallesViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SmartSolarDetallesFragment : Fragment() {

    private var _binding: FragmentSmartSolarDetallesBinding? = null
    private val binding get() = _binding!!
    private var _viewModel: SmartSolarDetallesViewModel? = null
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSmartSolarDetallesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = getViewModel()

        binding.tilEstadoAutoconsumidor.setEndIconOnClickListener {
            SmartSolarSolicitudAutoconsumoDialogFragment.newInstance().show(parentFragmentManager, "")
        }

        viewModel._state.observe(viewLifecycleOwner, Observer {
            binding.tietCAU.setText(it.cau)
            binding.tietEstadoAutoconsumidor.setText(it.estadoSolicitudAutoConsumidor)
            binding.tietCompensacion.setText(it.compensacionExcedentes)
            binding.tietTipoConsumo.setText(it.tipoAutoConsumo)
            binding.tietPotenciaInstalacion.setText(it.potenciaInstalacion)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): SmartSolarDetallesFragment {
            return SmartSolarDetallesFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}