package com.eagalts.exta.Extensions.CurrencyExtension

// In this file, you can add the desired currency or remove the unused one.
// IMPORTANT: When adding/removing currencies, be sure to correct the ExchangeRates file!

// =======================================================================================


// List of currencies, the current rate of which the program requests from the server
// Exchange rate to US dollar
// ISO currency codes used
val API_Currencies = listOf(
    "EUR", "RUB", "KRW", "CHF", "CNY", "AUD", "BRL", "CAD", "CZK", "DKK",
    "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY", "MXN", "NZD", "PLN", "SGD", "THB", "TRY"
)

// List of signs and currencies used
val ISOCurrencyCodes = mutableMapOf<String, String>(
    "₽" to "rub",
    "$" to "usd",
    "€" to "eur",
    "₩" to "krw",
    "₣" to "chf",
    "¥" to "cny",
    "A$" to "aud",
    "R" to "brl",
    "C$" to "cad",
    "Kč" to "czk",
    "kr" to "dkk",
    "£" to "gbp",
    "HK$" to "hkd",
    "Ft" to "huf",
    "Rp" to "idr",
    "₪" to "ils",
    "₹" to "inr",
    "¥" to "jpy",
    "NZ$" to "nzd",
    "zł" to "pln",
    "S$" to "sgd",
    "฿" to "thb",
    "₺" to "try"
)

// List of aliases that are used to denote a particular currency
// (any word -> ISO-code of the currency)
val additionalCurrencyCodes = mutableMapOf<String, String>(
    "канадский дол" to "cad",
    "канадских дол" to "cad",
    "canadian dol" to "cad",
    "australian dol" to "aud",
    "австралийский дол" to "aud",
    "австралийских дол" to "aud",
    "гонконгский дол" to "hkd",
    "гонконгских дол" to "hkd",
    "hong kong dol" to "hkd",
    "новозеландский дол" to "nzd",
    "новозеландских дол" to "nzd",
    "new zealand dol" to "nzd",
    "сингапурский дол" to "sgd",
    "сингапурских дол" to "sgd",
    "singapore dol" to "sgd",
    "доллар" to "usd",
    "dollar" to "usd",
    "евр" to "eur",
    "руб" to "rub",
    "won" to "krw",
    "вон" to "krw",
    "франк" to "chf",
    "frank" to "chf",
    "yuan" to "cny",
    "юан" to "cny",
    "реал" to "brl",
    "real" to "brl",
    "чешская крон" to "czk",
    "чешских крон" to "czk",
    "czech kron" to "czk",
    "датская крон" to "dkk",
    "датских крон" to "dkk",
    "danish kron" to "dkk",
    "фунт стер" to "gbp",
    "фунта стер" to "gbp",
    "фунтов стер" to "gbp",
    "форинт" to "huf",
    "forin" to "huf",
    "индийская рупи" to "inr",
    "индийских рупи" to "inr",
    "indian rup" to "inr",
    "рупи" to "idr",
    "rupi" to "idr",
    "шекел" to "ils",
    "shekel" to "ils",
    "иен" to "jpy",
    "yen" to "jpy",
    "злот" to "pln",
    "zlot" to "pln",
    "бат" to "thb",
    "baht" to "thb",
    "лир" to "try",
    "lir" to "try"
)