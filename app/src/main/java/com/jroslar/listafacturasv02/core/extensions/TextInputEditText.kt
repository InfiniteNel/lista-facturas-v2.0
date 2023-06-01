package com.jroslar.listafacturasv02.core.extensions

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

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