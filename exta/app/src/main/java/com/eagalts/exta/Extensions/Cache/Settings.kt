package com.eagalts.exta.Extensions.Cache

import android.content.Context
import android.content.SharedPreferences

class Settings (context : Context) {
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null

    private val prefsSetting = context.getSharedPreferences("databaseInfo", Context.MODE_PRIVATE)

    init {
        sharedPreferences = context.getSharedPreferences(getSizeInt().toString(), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun set(key : String, value : String) {
        editor?.putString(key, value)
        editor?.apply()
    }

    fun get(key : String): String {
        val value = sharedPreferences.getString(key, "")
        if (value != null) {
            return value
        }
        return ""
    }

    private fun getSizeInt(): Int { return prefsSetting.getInt("size", 0) }
}