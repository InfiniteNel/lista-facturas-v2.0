package com.jroslar.listafacturasv02.ui.view.smartsolar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.databinding.FragmentSmartSolarDetallesBinding
import com.jroslar.listafacturasv02.ui.viewmodel.smartsolar.SmartSolarDetallesViewModel

class SmartSolarDetallesFragment : Fragment() {

    private lateinit var smartSolarDetallesViewModel: SmartSolarDetallesViewModel
    private var _binding: FragmentSmartSolarDetallesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        smartSolarDetallesViewModel = ViewModelProvider(this)[SmartSolarDetallesViewModel::class.java].apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSmartSolarDetallesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tilEstadoAutoconsumidor.setEndIconOnClickListener {
            SmartSolarSolicitudAutoconsumoDialogFragment.newInstance().show(parentFragmentManager, "")
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(): SmartSolarDetallesFragment {
            return SmartSolarDetallesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, 1)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}