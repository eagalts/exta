package com.eagalts.exta.Extensions.CurrencyExtension

/*
==================================
      exta: extension file
==================================
*/

/*

Extension: Currencies
- Version: 1.0.2
- Development date: 04/06/2023
- Developer: eagalts
- Library: exta-main (standard extension)

Functional:
- Currency Converter

Required permissions:
- Internet access

*/

// If you want to make changes (add/remove currency, add alias),
// please edit the CurrencyCodes and ExchangeRates files.

import android.util.Log
import com.eagalts.exta.Activities.AppName
import com.eagalts.exta.Activities.CONTEXT
import com.eagalts.exta.Activities.MainActivity
import com.eagalts.exta.Extensions.Cache.Settings
import com.eagalts.exta.Extensions.CurrencyAPIKey
import com.eagalts.exta.aliasesExpression
import com.eagalts.exta.aliasesRussian
import com.eagalts.exta.aliasesString
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import org.mariuszgromada.math.mxparser.Expression
import java.io.IOException
import java.util.Locale

class CurrencyExtension {
    var errorCode : Boolean = false

    companion object {
        // API key located in "API_Keys.kt" file

        private val Currencies = API_Currencies.joinToString("%2C")
        val CurrencyURL = "https://api.freecurrencyapi.com/v1/latest?apikey=$CurrencyAPIKey&currencies=$Currencies"
    }

    // Update the exchange rate
    fun parseExchange(str : String) {
        // Send a request to the API server and translate the response into JSON format
        val mainObject = JSONObject(str)

        val value = get()
        if (value != str)
            save(str)

        for (i in ISOCurrencyCodes)
            if (i.value != "usd")
                ActualRate[i.value] = mainObject.getJSONObject("data").
                                                 getString(i.value.uppercase(Locale.getDefault())).toDouble()
    }

    private val client = OkHttpClient()

    // Function that generates a request for the exchange rate to the server
    fun runExchange() {
        Log.d(AppName, "Calling the exchange rate API...")

        val request = okhttp3.Request.Builder()
            .url(CurrencyURL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(AppName, e.toString())

                errorCode = true

                Log.d(AppName, "Trying to find saved currencies...")
                val value = get()
                if (value.isNotEmpty())
                    parseExchange(value)
            }
            override fun onResponse(call: Call, response: Response) {
                parseExchange(response.body()?.string().toString())
            }
        })
    }


    var convertInto = ""

    // The main function responsible for currency conversion
    fun currency(input_calculation : Double, input_string : String) : Double {
        var string = input_string
        var calculation = input_calculation

        try {
            if (ActualRate[ISOCurrencyCodes[convertInto]] == 0.00)
                return input_calculation

            val currentCurrency = findCurrency(string)
            string = formatISO(string)
            when {
                currentCurrency != "" -> {
                    string = string.replace(ISOCurrencyCodes[currentCurrency].toString(), "")

                    for (i in ISOCurrencyCodes)
                        ActualRate[i.value]

                    if (calculation.isNaN()) {
                        var expression = string
                        for (alias in aliasesString)
                            expression = string.replace(alias.key, alias.value)
                        for (alias in aliasesExpression)
                            expression = expression.replace(alias.key, alias.value)
                        for (alias in aliasesRussian)
                            expression = expression.replace(alias.key, alias.value)
                        expression = expression.replace(Regex("[A-Za-z]"), "")
                        expression = expression.replace(" ", "")
                        expression = expression.replace(Regex("[А-Яа-я]"), "")

                        calculation = Expression(expression).calculate()
                    }

                    convertInto = findCurrency(string)
                    if (convertInto != "") {
                        if (convertInto == "$")
                            calculation /= ActualRate[ISOCurrencyCodes[currentCurrency]]!!
                        else {
                            calculation = if (currentCurrency != "$")
                                (calculation * ActualRate[ISOCurrencyCodes[convertInto]]!!) /
                                        ActualRate[ISOCurrencyCodes[currentCurrency]]!!
                            else
                                (calculation * ActualRate[ISOCurrencyCodes[convertInto]]!!)
                        }
                    } else {
                        convertInto = currentCurrency
                    }
                }
            }
            return calculation
        }

        catch (e : Exception) {

            errorCode = true

            Log.e(AppName, "[CurrencyExtension] UNEXPECTED ERROR: $e")
            return input_calculation
        }
    }

    // Function that determines the specified currency
    private fun findCurrency(input_string : String) : String {
        var r = ""

        val string = formatISO(input_string)
        val nearestId = mutableListOf<Int>()

        for (i in ISOCurrencyCodes)
            nearestId.add(matchDetails(string, i.value))

        nearestId.sort()
        for (i in 0 until nearestId.size) nearestId.remove(-1)

        if (nearestId.size != 0) {
            val currentCurrency = string[nearestId[0]] + "" + string[nearestId[0]+1] + "" +
                    string[nearestId[0] + 2]

            for (i in ISOCurrencyCodes)
                if (currentCurrency.contains(i.value))
                    r += i.key

        }
        return r
    }

    // Convert arbitrary currency names to ISO standard
    private fun formatISO(input_string: String) : String {
        var string = input_string

        for (i in additionalCurrencyCodes)
            string = string.replace(i.key, i.value)

        for (i in ISOCurrencyCodes)
            string = string.replace(i.key, i.value)
        return string
    }

    // Finding the location of a substring in a string
    private fun matchDetails(inputString: String, whatToFind: String, startIndex: Int = 0): Int {
        val matchIndex = inputString.indexOf(whatToFind, startIndex)
        return if (matchIndex >= 0) matchIndex else -1
    }

    // Saving currencies
    private fun save(str : String) {
        val settings = Settings(CONTEXT)
        settings.set("Currency", str)
    }

    private fun get() : String {
        val settings = Settings(CONTEXT)
        return settings.get("Currency")
    }
}