package com.jroslar.listafacturasv02.ui.view.smartsolar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jroslar.listafacturasv02.databinding.FragmentSmartSolarMiInstalacionBinding

class SmartSolarMiInstalacionFragment : Fragment() {

    private var _binding: FragmentSmartSolarMiInstalacionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSmartSolarMiInstalacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): SmartSolarMiInstalacionFragment {
            return SmartSolarMiInstalacionFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}