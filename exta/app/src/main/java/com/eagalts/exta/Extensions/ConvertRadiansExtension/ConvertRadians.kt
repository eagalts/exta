package com.eagalts.exta.Extensions.ConvertRadiansExtension

import android.util.Log
import com.eagalts.exta.Activities.AppName
import java.util.regex.Pattern

fun convertDegreesToRadians(input: String): String {
    // Регулярное выражение для поиска тригонометрических функций
    val pattern = Pattern.compile("(sin|cos|tan|sinh|cosh|tanh)\\(([^)]*)\\)")
    val matcher = pattern.matcher(input)
    val result = StringBuffer()

    while (matcher.find()) {
        val function = matcher.group(1) // sin, cos, tan, etc.
        val argument = matcher.group(2) // содержимое внутри скобок

        // Преобразуем градусы в радианы
        val convertedArgument = convertExpressionDegreesToRadians(argument)
        matcher.appendReplacement(result, "$function($convertedArgument)")

        //Log.d(AppName, "Convert: F:$function | A:$argument | C:$convertedArgument")
    }

    matcher.appendTail(result)
    return result.toString()
}

fun convertExpressionDegreesToRadians(expression: String): String {
    // Преобразуем выражение из градусов в радианы
    val pattern = Pattern.compile("(\\d+(\\.\\d+)?)")
    val matcher = pattern.matcher(expression)
    val result = StringBuffer()

    while (matcher.find()) {
        val degrees = matcher.group(1).toDouble()
        val radians = Math.toRadians(degrees)
        matcher.appendReplacement(result, radians.toString())

        //Log.d(AppName, "Convert: D:$degrees | R:$radians")
    }

    matcher.appendTail(result)
    return result.toString()
}

fun processInputString(inputString: String, usesDeg: Boolean): String {
    return if (!usesDeg) {
        inputString
    } else {
        convertDegreesToRadians(inputString)
    }
}