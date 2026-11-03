package com.eagalts.exta.BugFixes.AlgebraOfLogic

import java.util.Locale

/*
==================================
      exta: extension file
==================================
*/

/*

Extension: Algebra of logic
- Version: 1.0.0
- Development date: 13/07/2024
- Developer: eagalts
- Library: exta-main (standard extension)

Functional:
- Scales used for shorter form of writing big numbers.

*/

class Scales {
    fun expandScales(input: String, locale: Locale = Locale("ru")): String {
        val scales = mapOf(
            "k" to 1_000,
            "к" to 1_000,
            "thousand" to 1_000,
            "тысяча" to 1_000,
            "тысячи" to 1_000,
            "тысяч" to 1_000,
            "тыс" to 1_000,

            "M" to 1_000_000,
            "kk" to 1_000_000,
            "кк" to 1_000_000,
            "million" to 1_000_000,
            "млн" to 1_000_000,
            "миллион" to 1_000_000,
            "миллиона" to 1_000_000,
            "миллионов" to 1_000_000,

            "B" to 1_000_000_000,
            "billion" to 1_000_000_000,
            "миллиард" to 1_000_000_000,
            "миллиарда" to 1_000_000_000,
            "миллиардов" to 1_000_000_000,
            "млрд" to 1_000_000_000
        )

        val regex = """(\d+)([kкMМB]|[ \t]*[thousandтысмиллионmillionмиллиардмлрдтысячатысячитысячkkккмиллионамиллионовмлн]+)""".toRegex()

        return regex.replace(input) { matchResult ->
            val number = matchResult.groups[1]?.value?.toLongOrNull() ?: return@replace matchResult.value
            val scale = matchResult.groups[2]?.value?.trim() ?: ""

            val multiplier = scales[scale.toLowerCase(locale)] ?: return@replace matchResult.value
            (number * multiplier).toString()
        }
    }
}
