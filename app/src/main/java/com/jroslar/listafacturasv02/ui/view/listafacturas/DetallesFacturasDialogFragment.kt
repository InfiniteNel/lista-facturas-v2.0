package com.jroslar.listafacturasv02.ui.view.listafacturas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jroslar.listafacturasv02.databinding.DialogfragmentDetallesFacturasBinding


class DetallesFacturasDialogFragment : DialogFragment() {

    private var _binding: DialogfragmentDetallesFacturasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DialogfragmentDetallesFacturasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btCerrarPopUpDetallesFacturas.setOnClickListener {
            dismiss()
        }
    }
}