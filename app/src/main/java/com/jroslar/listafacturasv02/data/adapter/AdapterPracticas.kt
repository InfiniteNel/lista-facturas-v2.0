package com.jroslar.listafacturasv02.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jroslar.listafacturasv02.R
import com.jroslar.listafacturasv02.databinding.ItemPracticaBinding

class AdapterPracticas(private val listener: OnManagePractica) :
    RecyclerView.Adapter<AdapterPracticas.ViewHolder>() {

    interface OnManagePractica {
        fun onClickPractica(practica: Practica)
    }

    enum class PracticasName {
        PRACTICA1,
        PRACTICA2
    }

    data class Practica (
        val name:String,
        val tipo: PracticasName
    )

    val listaPracticas = listOf(
        Practica("Práctica 1", PracticasName.PRACTICA1),
        Practica("Práctica 2", PracticasName.PRACTICA2)
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPracticaBinding.bind(itemView)

        fun bind(practica: Practica, listener: OnManagePractica) {
            binding.tvTitlePractica.text = practica.name
            binding.ivbArrow.setOnClickListener { listener.onClickPractica(practica) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateLayout = LayoutInflater.from(parent.context)
        return ViewHolder(inflateLayout.inflate(R.layout.item_practica, parent, false))
    }

    override fun getItemCount(): Int {
        return listaPracticas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaPracticas[position], listener)
    }
}