package com.jroslar.listafacturasv02.core.extensions

import android.content.Context

fun Int.getResourceStringAndroid(context: Context): String {
    return context.resources.getString(this)
}