package com.jroslar.listafacturasv02.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jroslar.listafacturasv02.data.database.model.FacturaEntity
import com.jroslar.listafacturasv02.data.model.FacturaModel

@Dao
interface FacturasDao {
    @Insert
    fun insertFacturas(facturas: List<FacturaEntity>)
    @Query("DELETE from factura_entity")
    fun clearDataBase()
    @Query("SELECT * from factura_entity")
    fun getFacturas(): List<FacturaEntity>
}