package com.eagalts.exta.Extensions.Cache

import android.content.Context
import android.content.SharedPreferences

class FirstLaunch (context : Context) {
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null

    private val prefsSetting = context.getSharedPreferences("databaseInfo", Context.MODE_PRIVATE)

    init {
        sharedPreferences = context.getSharedPreferences(getSizeInt().toString(), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun set() {
        editor?.putString("firstLaunch", true.toString())
        editor?.apply()
    }

    fun get(): Boolean {
        val value = sharedPreferences.getString("firstLaunch", "")
        if (value != null) {
            return value.toBoolean()
        }
        return false
    }

    private fun getSizeInt(): Int { return prefsSetting.getInt("size", 0) }
}