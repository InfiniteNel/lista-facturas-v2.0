package com.jroslar.listafacturasv02.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FacturasModel(
    val numFacturas: Int,
    val facturas: List<FacturaModel>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        TODO("facturas")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(numFacturas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FacturasModel> {
        override fun createFromParcel(parcel: Parcel): FacturasModel {
            return FacturasModel(parcel)
        }

        override fun newArray(size: Int): Array<FacturasModel?> {
            return arrayOfNulls(size)
        }
    }
}
