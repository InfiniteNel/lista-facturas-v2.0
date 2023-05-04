package com.jroslar.listafacturasv02.data.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.core.Constantes.Companion.DescEstado
import com.jroslar.listafacturasv02.core.Constantes.Companion.MONEDA_VALUE
import com.jroslar.listafacturasv02.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasv02.data.model.FacturaModel
import com.jroslar.listafacturasv02.databinding.ItemFacturasBinding
import java.time.format.DateTimeFormatter
import java.util.*

class ListaFacturasAdapter(private val listener: OnManageFactura):
    RecyclerView.Adapter<ListaFacturasAdapter.ViewHolder>() {
    var listaFacturas: List<FacturaModel> = emptyList()

    interface OnManageFactura {
        fun onClickFactura(factura: FacturaModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFacturasBinding.bind(itemView)

        fun bind(factura: FacturaModel, listener: OnManageFactura) {
            var tipo = factura.descEstado
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val newdf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
                var fecha = factura.fecha.castStringToDate().format(newdf)
                binding.tvFacturaFecha.text = fecha.substring(0,4).uppercase() + fecha.substring(4)
            } else binding.tvFacturaFecha.text = factura.fecha

            binding.tvFacturaPrecio.text = factura.importeOrdenacion.toString().plus(MONEDA_VALUE)
            binding.tvFacturaTipo.text = tipo
            when(tipo) {
                DescEstado.PedienteDePago.descEstado -> binding.tvFacturaTipo.setTextColor(Color.RED)
                else -> binding.tvFacturaTipo.setTextColor(Color.GRAY)
            }

            itemView.setOnClickListener { listener.onClickFactura(factura) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateLayout = LayoutInflater.from(parent.context)
        return ViewHolder(inflateLayout.inflate(R.layout.item_facturas, parent, false))
    }

    override fun getItemCount(): Int {
        return listaFacturas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaFacturas[position], listener)
    }
}