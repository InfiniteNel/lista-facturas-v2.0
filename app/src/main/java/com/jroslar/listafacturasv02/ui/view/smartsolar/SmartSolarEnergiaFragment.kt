package com.jroslar.listafacturasv02.ui.view.smartsolar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jroslar.listafacturasv02.databinding.FragmentSmartSolarEnergiaBinding

class SmartSolarEnergiaFragment : Fragment() {
    private var _binding: FragmentSmartSolarEnergiaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSmartSolarEnergiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): SmartSolarEnergiaFragment {
            return SmartSolarEnergiaFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}