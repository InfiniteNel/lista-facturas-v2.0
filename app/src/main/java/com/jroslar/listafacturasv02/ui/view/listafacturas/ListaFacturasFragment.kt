package com.jroslar.listafacturasv02.ui.view.listafacturas

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.data.adapter.ListaFacturasAdapter
import com.jroslar.listafacturasv02.data.model.FacturaModel
import com.jroslar.listafacturasv02.data.model.FacturasModel
import com.jroslar.listafacturasv02.databinding.FragmentListaFacturasBinding
import com.jroslar.listafacturasv02.ui.viewmodel.listafacturas.ListaFacturasViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ListaFacturasFragment : Fragment(), ListaFacturasAdapter.OnManageFactura, MenuProvider {

    private var _binding: FragmentListaFacturasBinding? = null
    private val binding get() = _binding!!
    private var _adapter: ListaFacturasAdapter? = null
    private val adapter get() = _adapter!!
    private var _viewModel: ListaFacturasViewModel? = null
    private val viewModel get() = _viewModel!!

    companion object {
        const val MAX_IMPORTE: String = "maximo_importe"
        const val DATA_FILTER: String = "data_filter"
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.filtrarFacturas -> {
                if (viewModel._state.value != ListaFacturasViewModel.ListaFacturasResult.API_NO_DATA
                    || viewModel._state.value != ListaFacturasViewModel.ListaFacturasResult.LOADING) {
                    val bundle = Bundle()
                    bundle.putFloat(MAX_IMPORTE, viewModel._maxValueImporte.value?: 0F)
                    findNavController().navigate(R.id.action_ListaFacturasFragment_to_filtrarFacturasFragment, bundle)
                }
                true
            }
            else -> true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListaFacturasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        _viewModel = getViewModel()
        _adapter = ListaFacturasAdapter(this, requireContext())

        intiAdapter()

        viewModel._data.observe(viewLifecycleOwner) {
            adapter.listaFacturas = it
            adapter.notifyDataSetChanged()
        }

        viewModel._state.observe(viewLifecycleOwner) {
            when (it) {
                ListaFacturasViewModel.ListaFacturasResult.LOADING -> {
                    binding.loading.isVisible = true
                    binding.tvTitleNoDataListaFacturas.isVisible = false
                }
                ListaFacturasViewModel.ListaFacturasResult.DATA -> {
                    binding.loading.isVisible = false
                    binding.tvTitleNoDataListaFacturas.isVisible = false
                }
                ListaFacturasViewModel.ListaFacturasResult.NO_DATA -> {
                    showNoData()
                }
                ListaFacturasViewModel.ListaFacturasResult.API_NO_DATA -> {
                    showNoData()
                }
                else -> {
                    showNoData()
                }
            }
        }

        setFragmentResultListener(DATA_FILTER) { reqKey, bundle ->
            if (reqKey == DATA_FILTER) {
                val value: FacturasModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(DATA_FILTER, FacturasModel::class.java)!!
                } else {
                    @Suppress("DEPRECATION") bundle.getParcelable(DATA_FILTER)!!
                }
                viewModel.getList(value.facturas)
            }
        }
    }

    private fun showNoData() {
        binding.loading.isVisible = false
        binding.tvTitleNoDataListaFacturas.isVisible = true
    }

    private fun intiAdapter() {
        binding.rvListaFacturas.layoutManager = LinearLayoutManager(context)
        binding.rvListaFacturas.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickFactura(factura: FacturaModel) {
        findNavController().navigate(R.id.popUpFragment)
    }
}