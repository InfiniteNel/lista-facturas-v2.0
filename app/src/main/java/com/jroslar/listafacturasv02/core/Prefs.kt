package com.jroslar.listafacturasv02.core

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    val PREFS_NAME = "com.jroslar.listafacturasv02"
    val USER_EMAIL = "user_email"
    val USER_PASSWORD = "user_password"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var email: String
        get() = EncryptHelper.decryptString(prefs.getString(USER_EMAIL, "")?:"")?:""
        set(value) = prefs.edit().putString(USER_EMAIL, EncryptHelper.encryptString(value)).apply()

    var password: String
        get() = EncryptHelper.decryptString(prefs.getString(USER_PASSWORD, "")?:"")?:""
        set(value) = prefs.edit().putString(USER_PASSWORD, EncryptHelper.encryptString(value)).apply()
}