package com.eagalts.exta

val aliasesString = mutableMapOf(
    " ." to "",
    " ," to "",
    " от " to "*",
    " of " to "*",
    " по " to "*",
    " times " to "*",
    " mul " to "*",
    " на " to "*",
)

val timeAliases = mutableMapOf(
    " minutes" to " мин",
    " minute" to " мин",
    " mins" to " мин",
    " min" to " мин",

    " days" to " дни",
    " day" to " дни",

    " hours" to " час",
    " hour" to " час",
    " hrs" to " час",

    " seconds" to " сек",
    " secons" to " сек",
    " sec" to " сек",

    " to " to " в ",
    " in " to " в ",
)

val aliasesExpression = mutableMapOf(
    "÷" to "/",
    "×" to "*",
    "×" to "*",
    "•" to "*",
    "√" to "sqrt",
    "π" to "pi",

    "¹" to "^1",
    "²" to "^2",
    "³" to "^3",
    "⁴" to "^4",
    "⁵" to "^5",
    "⁶" to "^6",
    "⁷" to "^7",
    "⁸" to "^8",
    "⁹" to "^9",
    "⁰" to "^0",
)

val aliasesRussian  = mutableMapOf(
    "округ(" to "round(",
    "целое(" to "round(",
    "целоевверх(" to "ceil(",
    "вниз(" to "floor(",
    "вверх(" to "ceil(",
    "модуль(" to "abs(",
    "корень(" to "sqrt(",

    "синус(" to "sin(",
    "косинус(" to "cos(",
    "тангенс(" to "tan(",
    "асинус(" to "sin(",
    "акосинус(" to "cos(",
    "атангенс(" to "tan(",
    "гсинус(" to "sin(",
    "гкосинус(" to "cos(",
    "гтангенс(" to "tan(",

    "среднее(" to "mean(",
    "дисперс(" to "var(",
    "отклон(" to "std(",

    "производ(" to "der(",
    "производная(" to "der(",
    "производ-(" to "der-(",
    "производ+(" to "der+(",
    "производная-(" to "der-(",
    "производная+(" to "der+(",

    "инт(" to "int(",
    "интеграл(" to "int(",

    "если(" to "int(",
)