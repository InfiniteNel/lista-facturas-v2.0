package com.jroslar.listafacturasv02.core

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
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
                date = LocalDate.parse(validateDate(this), df)
            } else if ("\\d{1,2} [A-Z a-z]{3} \\d{4}".toRegex().matches(this)) {
                val df: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale(Locale.getDefault().displayLanguage))
                date = LocalDate.parse(this, df)
            }
            return date
        }

        private fun validateDate(value:String):String {
            val list = value.split("/").toMutableList()
            if (list[0].toInt() < 10 && list[0].length < 2) {
                list[0] = "0${list[0]}"
            }
            if (list[1].toInt() < 10 && list[1].length < 2) {
                list[1] = "0${list[1]}"
            }
            return "${list[0]}/${list[1]}/${list[2]}"
        }

        fun TextInputEditText.onTextChanged(listener: (String) -> Unit) {
            this.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //
                }
                override fun afterTextChanged(p0: Editable?) {
                    listener(p0.toString())
                }
            })
        }
    }



}