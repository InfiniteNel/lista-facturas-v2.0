package com.jroslar.listafacturasv02.ui.view.smartsolar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jroslar.listafacturasv02.databinding.DialogfragmentSmartSolarSolicitudAutoconsumoBinding


class SmartSolarSolicitudAutoconsumoDialogFragment : DialogFragment() {
    private var _binding: DialogfragmentSmartSolarSolicitudAutoconsumoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DialogfragmentSmartSolarSolicitudAutoconsumoBinding.inflate(inflater, container, false)
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance(): SmartSolarSolicitudAutoconsumoDialogFragment {
            return SmartSolarSolicitudAutoconsumoDialogFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vtAceptarSmartSolar.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}