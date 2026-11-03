package com.eagalts.exta.Extensions.TimeMathExtension

/*
==================================
      exta: extension file
==================================
*/

/*

Extension: TimeMath
- Version: 1.0.0
- Development date: 12.08.2024
- Developer: eagalts
- Library: exta-main (standard extension)
- First delivery version: 2.1.0 [7]

Functional:
- Mathematical operations with time
    * Addition
    * Subtraction
- Conversion of quantities

*/

import java.time.LocalTime
import java.time.Duration
import kotlin.math.abs
import android.content.Context
import com.eagalts.exta.R

class TimeMathExtension() {
    fun runExtension(context: Context, expression: String) : String? {
        return timeConvertation(context = context, expression = expression)
    }

    fun timeConvertation(context: Context, expression: String): String? {
        // Регулярные выражения для различных преобразований
        // Дни -> ..
        val daysToHoursRegex = Regex("""(\d+)\s*дн(?:ей|я|[еи]|ays?|d)?\s*в\s*час(?:ов|ov|ы|ах)?""")
        val daysToMinutesRegex = Regex("""(\d+)\s*дн(?:ей|я|[еи]|ays?|d)?\s*в\s*мин(?:ут[ы]?|u[ts]|m|ах)?""")
        // Часы -> ..
        val hoursToDaysRegex = Regex("""(\d{1,3})\s*час(?:а|ов|ov)?\s*в\s*дн(?:ей|я|[еи]|ays?|d|ях)?""")
        val hoursToMinutesRegex = Regex("""(\d{1,3})\s*час(?:а|ов|ov)?\s*в\s*мин(?:ут[ы|ах]?|u[ts]|m|ах)?""")
        val hoursToSecondsRegex = Regex("""(\d{1,3})\s*час(?:а|ов|ov)?\s*в\s*сек(?:унд[ы|ах]?|onds?|s|ах)?""")
        // Минуты -> ..
        val minutesToHoursRegex = Regex("""(\d+)\s*мин(?:ут[ы]?|u[ts]|m)?\s*в\s*час(?:ов|ov|ах)?""")
        val minutesToSecondsRegex = Regex("""(\d+)\s*мин(?:ут[ы]?|u[ts]|m)?\s*в\s*сек(?:унд[ы|ах]?|onds?|s|ах)?""")
        // Секунды -> ..
        val secondsToHoursRegex = Regex("""(\d+)\s*сек(?:унд[ы]?|onds?|s)?\s*в\s*час(?:ов|ov|ах|ы)?""")
        val secondsToMinutesRegex = Regex("""(\d+)\s*сек(?:унд[ы]?|onds?|s)?\s*в\s*мин(?:ут[ы|ах]?|u[ts]|m)?""")
        // Время -> ..
        val timeToMinutesRegex = Regex("""(\d{1,3}):(\d{2})\s*в\s*мин(?:ут[ы]?|u[ts]|m)?""")

        return when {
            timeToMinutesRegex.matches(expression) -> {
                val matchResult = timeToMinutesRegex.find(expression)
                val (hours, minutes) = matchResult!!.destructured
                val totalMinutes = hours.toInt() * 60 + minutes.toInt()
                "$totalMinutes ${context.getString(R.string.minutes_abbr)}"
            }
            hoursToMinutesRegex.matches(expression) -> {
                val matchResult = hoursToMinutesRegex.find(expression)
                val hours = matchResult!!.groupValues[1].toInt()
                val totalMinutes = hours * 60
                "$totalMinutes ${context.getString(R.string.minutes_abbr)}"
            }
            minutesToHoursRegex.matches(expression) -> {
                val matchResult = minutesToHoursRegex.find(expression)
                val totalMinutes = matchResult!!.groupValues[1].toInt()
                val hours = totalMinutes / 60
                val minutes = totalMinutes % 60
                "${hours}${context.getString(R.string.hours_abbr)} ${minutes}${context.getString(R.string.minutes_abbr)}"
            }
            hoursToSecondsRegex.matches(expression) -> {
                val matchResult = hoursToSecondsRegex.find(expression)
                val hours = matchResult!!.groupValues[1].toInt()
                val totalSeconds = hours * 3600
                "$totalSeconds ${context.getString(R.string.seconds_abbr)}"
            }
            minutesToSecondsRegex.matches(expression) -> {
                val matchResult = minutesToSecondsRegex.find(expression)
                val minutes = matchResult!!.groupValues[1].toInt()
                val totalSeconds = minutes * 60
                "$totalSeconds ${context.getString(R.string.seconds_abbr)}"
            }
            hoursToDaysRegex.matches(expression) -> {
                val matchResult = hoursToDaysRegex.find(expression)
                val hours = matchResult!!.groupValues[1].toInt()
                val totalDays = hours / 24
                val remainingHours = hours % 24
                "${totalDays}${context.getString(R.string.days_abbr)} ${remainingHours}${context.getString(R.string.hours_abbr)}"
            }
            daysToHoursRegex.matches(expression) -> {
                val matchResult = daysToHoursRegex.find(expression)
                val days = matchResult!!.groupValues[1].toInt()
                val totalHours = days * 24
                "$totalHours ${context.getString(R.string.hours_abbr)}"
            }
            daysToMinutesRegex.matches(expression) -> {
                val matchResult = daysToMinutesRegex.find(expression)
                val days = matchResult!!.groupValues[1].toInt()
                val totalMinutes = days * 24 * 60
                "$totalMinutes ${context.getString(R.string.minutes_abbr)}"
            }
            secondsToMinutesRegex.matches(expression) -> {
                val matchResult = secondsToMinutesRegex.find(expression)
                val seconds = matchResult!!.groupValues[1].toInt()
                val totalMinutes = seconds / 60
                val remainingSeconds = seconds % 60
                "${totalMinutes}${context.getString(R.string.minutes_abbr)} ${remainingSeconds}${context.getString(R.string.seconds_abbr)}"
            }
            secondsToHoursRegex.matches(expression) -> {
                val matchResult = secondsToHoursRegex.find(expression)
                val seconds = matchResult!!.groupValues[1].toInt()
                val totalHours = seconds / 3600
                val remainingMinutes = (seconds % 3600) / 60
                "${totalHours}${context.getString(R.string.hours_abbr)} ${remainingMinutes}${context.getString(R.string.minutes_abbr)}"
            }
            else -> {
                calculateTimeExpression(context, expression)
            }
        }
    }


    fun calculateTimeExpression(context : Context, expression: String): String? {
        val regex = Regex("""([-+])?\s*(\d{1,3}):(\d{2})""")
        val matches = regex.findAll(expression)

        if (matches.none()) return null // Если строка не содержит времени, вернуть null

        var totalMinutes = 0L
        var initialTime: LocalTime? = null

        for (match in matches) {
            val sign = match.groupValues[1]
            val hours = match.groupValues[2].toLong()
            val minutes = match.groupValues[3].toLong()

            val currentDuration = Duration.ofHours(hours).plusMinutes(minutes)

            if (initialTime == null && sign.isEmpty()) {
                // Если это первая встреченная временная метка и перед ней нет знака, считаем это начальным временем
                initialTime = LocalTime.of(hours.toInt() % 24, minutes.toInt() % 60)
                totalMinutes = hours * 60 + minutes
            } else {
                // Обрабатываем операции с временем
                val durationInMinutes = currentDuration.toMinutes()

                totalMinutes = if (sign == "-") {
                    totalMinutes - durationInMinutes
                } else {
                    totalMinutes + durationInMinutes
                }
            }
        }

        // Обработка результатов
        val days = totalMinutes / (24 * 60)
        val time = LocalTime.ofSecondOfDay((abs(totalMinutes) % (24 * 60)) * 60)

        return if (days > 0) {
            "${days}${context.getString(R.string.days_abbr)} $time"
        } else {
            time.toString()
        }
    }
}