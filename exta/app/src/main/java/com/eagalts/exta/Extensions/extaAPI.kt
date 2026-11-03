package com.eagalts.exta.Extensions

import android.util.Log
import com.eagalts.exta.Activities.AppName

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class extaAPI {
    var version = ""

    fun getVersionFromAPI() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(ExtaAPIKey)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(AppName, "[extaAPI] Internal error: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonStr = response.body()?.string()
                version = parse(jsonStr!!).toString()
            }
        })
    }

    private fun parse(jsonStr: String): String? {
        val json = JSONObject(jsonStr)
        val dataArr = json.getJSONArray("data")
        for (i in 0 until dataArr.length()) {
            val appObj = dataArr.getJSONObject(i)
            if (appObj.getString("AppName") == "exta") {
                return appObj.getString("Version")
            }
        }
        return null
    }
}