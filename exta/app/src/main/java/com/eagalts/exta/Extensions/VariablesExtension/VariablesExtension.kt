package com.eagalts.exta.Extensions.VariablesExtension

/*
==================================
      exta: extension file
==================================
*/

/*

Extension: Variables
- Version: 1.0.0
- Development date: 01/14/2023
- Developer: eagalts
- Library: exta-main (standard extension)

Functional:
- Working with variables

*/

import android.util.Log
import com.eagalts.exta.Activities.AppName
import org.mariuszgromada.math.mxparser.Expression

class VariablesExtension {
    private val map = mutableMapOf<String, Double>()

    fun variablesHandler(inputString: String): String {
        if (inputString.isEmpty())
            return inputString
        else if (inputString.last() == '=')
            return inputString

        try {
            var replacementVariableFlag = false
            var neededUpdateOfVariableFlag = false
            val regexSymbolsPattern = Regex(pattern = "[^\\w\\.]+")

            var newString = inputString
            val mapInputString = newString.split(regexSymbolsPattern)
            var mutableVariable = ""

            for (element in mapInputString) {
                if (map.containsKey(element)) {
                    if (checkIfNeedVariableUpdate(inputString, neededUpdateOfVariableFlag)) {
                        neededUpdateOfVariableFlag = true
                        newString = newString.replaceFirst(element, "%variable%")
                        mutableVariable = element
                    } else {
                        replacementVariableFlag = true
                        newString = newString.replace(element, getVariable(element))
                    }
                } else {
                    if (checkIfNeedVariableUpdate(inputString, neededUpdateOfVariableFlag)) {
                        neededUpdateOfVariableFlag = true
                        newString = newString.replaceFirst(element, "%variable%")
                        mutableVariable = element
                    }
                }
            }

            if (mutableVariable.isNotEmpty())
                newString = newString.replace("%variable%", mutableVariable)

            return if (neededUpdateOfVariableFlag)
                varUpdater(newString, regexSymbolsPattern)
            else if (replacementVariableFlag)
                newString
            else {
                if (inputString.count { it == '=' } == 1)
                    varUpdater(inputString, regexSymbolsPattern)
                else
                    newString
            }
        } catch (exception: Exception) {
            Log.e(AppName, "Error executing block 'VariablesExtension': $exception.")
            return ""
        }
    }


    private fun getVariable(variableName: String): String {
        return map[variableName].toString()
    }

    private fun varUpdater(inputString: String, regexSymbolsPattern: Regex): String {
        val splitStr = inputString.split("=")

        val variableName = splitStr[0].replace(regexSymbolsPattern, "")
        val variableValue = splitStr[1]

        val letters = Regex("[a-zA-Zа-яА-Я\\=\\ ]")
        val expression = variableValue.replace(letters, "")
        val calculation = Expression(expression).calculate()

        setVariable(variableName, calculation)

        return calculation.toString()
    }

    private fun checkIfNeedVariableUpdate(
        inputString: String,
        neededUpdateOfVariableFlag: Boolean
    ): Boolean {
        return (inputString.contains("=") && !neededUpdateOfVariableFlag)
    }

    private fun setVariable(variableName: String, variableValue: Double) {
        map[variableName] = variableValue
    }

    fun clearAllVariables() {
        map.clear()
    }
}