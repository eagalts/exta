package com.eagalts.exta.Extensions.Cache

import android.content.Context
import android.content.SharedPreferences

class SaveLoadNote (context: Context) {
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null

    private val prefsSetting = context.getSharedPreferences("databaseInfo", Context.MODE_PRIVATE)

    init {
        sharedPreferences = context.getSharedPreferences(getSizeInt().toString(), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun getSizeInt(): Int { return prefsSetting.getInt("size", 0) }

    fun saveAll(blockString : String, resultString : String) {
        editor?.putString("savedNoteText", blockString)
        editor?.putString("savedNoteResults", resultString)
        editor?.apply()
    }

    fun readBlock() : String {
        val str = sharedPreferences.getString("savedNoteText", "")
        if (str != null) {
            return str
        }
        return ""
    }

    fun readResults() : String {
        val str = sharedPreferences.getString("savedNoteResults", "")
        if (str != null) {
            return str
        }
        return ""
    }
}