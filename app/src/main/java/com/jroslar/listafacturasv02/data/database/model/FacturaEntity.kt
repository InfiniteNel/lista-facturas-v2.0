package com.jroslar.listafacturasv02.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jroslar.listafacturasv02.data.model.FacturaModel

@Entity(tableName = "factura_entity")
data class FacturaEntity (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "descEstado")  val descEstado: String,
    @ColumnInfo(name = "importeOrdenacion") val importeOrdenacion: Float,
    @ColumnInfo(name = "fecha") val fecha: String
)

fun FacturaEntity.toModelFactura(): FacturaModel = FacturaModel(descEstado, importeOrdenacion, fecha)