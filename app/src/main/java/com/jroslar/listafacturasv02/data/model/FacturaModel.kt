package com.jroslar.listafacturasv02.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "factura_entity")
data class FacturaModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("descEstado") @ColumnInfo(name = "descEstado")  val descEstado: String,
    @SerializedName("importeOrdenacion") @ColumnInfo(name = "importeOrdenacion") val importeOrdenacion: Float,
    @SerializedName("fecha") @ColumnInfo(name = "fecha") val fecha: String,
)
