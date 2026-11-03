package com.eagalts.exta.Extensions.CurrencyExtension

//Current exchange rate (updated automatically)

// IMPORTANT: When adding a new currency, it is recommended to specify the value "0.00".
// If you specify a different value, then it will be used as the main course
// when there is no Internet connection.

val ActualRate = mutableMapOf<String, Double> (
    "rub" to 0.00,
    "eur" to 0.00,
    "krw" to 0.00,
    "chf" to 0.00,
    "cny" to 0.00,
    "aud" to 0.00,
    "brl" to 0.00,
    "cad" to 0.00,
    "czk" to 0.00,
    "dkk" to 0.00,
    "gbp" to 0.00,
    "hkd" to 0.00,
    "huf" to 0.00,
    "idr" to 0.00,
    "ils" to 0.00,
    "inr" to 0.00,
    "jpy" to 0.00,
    "nzd" to 0.00,
    "pln" to 0.00,
    "sgd" to 0.00,
    "thb" to 0.00,
    "try" to 0.00
)