package com.jroslar.listafacturasv02.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Extensions {

    companion object{
        fun Int.getResourceStringAndroid(context: Context): String {
            return context.resources.getString(this)
        }
        @RequiresApi(Build.VERSION_CODES.O)
        fun String.castStringToDate(): LocalDate {
            var date: LocalDate = LocalDate.now()
            if ("\\d{1,2}/\\d{1,2}/\\d{4}".toRegex().matches(this)) {
                val df: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                return LocalDate.parse(validateDate(this), df)
            } else if ("\\d{1,2} [A-Z a-z]{3} \\d{4}".toRegex().matches(this)) {
                val df: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
                return LocalDate.parse(this, df)
            }
            return date
        }

        private fun validateDate(value:String):String {
            var list = value.split("/").toMutableList()
            if (list[0].toInt() < 10 && list[0].length < 2) list[0] = "0${list[0]}"
            if (list[1].toInt() < 10 && list[1].length < 2) list[1] = "0${list[1]}"
            return "${list[0]}/${list[1]}/${list[2]}"
        }
    }



}