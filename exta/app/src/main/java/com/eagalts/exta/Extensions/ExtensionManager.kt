package com.eagalts.exta.Extensions

/** Before using the extension, you need to import it below! **/
// Currency Extension (standard library)
import com.eagalts.exta.Extensions.CurrencyExtension.CurrencyExtension

// Variables Extension (standard library)
import com.eagalts.exta.Extensions.VariablesExtension.VariablesExtension

// Algebra of logic extension (standard library)
import com.eagalts.exta.BugFixes.AlgebraOfLogic.AlgebraOfLogic
import com.eagalts.exta.BugFixes.AlgebraOfLogic.Scales
import com.eagalts.exta.Extensions.TimeMathExtension.TimeMathExtension

// Function for converting radians to degrees


/** Initialization area **/
// CurrencyExtension using
val currencyExtension = CurrencyExtension()

// VariablesExtension using
val variablesExtension = VariablesExtension()

// AlgebraOfLogicExtension using
val alogExtension = AlgebraOfLogic()

// Scales using
val scales = Scales()


/** Enable/disable the use of extensions **/
// Currency Extension (standard library)
var USE_CURRENCY_EXTENSION = true

// Variables Extension (standard library)
var USE_VARIABLES_EXTENSION = true

// Algebra Of Logic Extension (standard library)
val USE_ALOG_EXTENSION = true

// Use saves when exiting the app
var USE_SAVES = true

// Using radians (false) or degrees (true) when calculating trigonometric functions
var USE_DEGREES_IN_CALCULATION = false

// Use scales form
var USE_SCALES = true

// Using TimeMathExtension
var USE_TIME_MATH = true

/** Functions **/

// The main function of calling extensions
fun extensionExec(inputCalculation : Double, inputExpression : String) : Double {

    // Use the 'calculation' and 'string' variables in the extensions.

    // If initial values are required, then use the static variables
    // 'input_calculation' and 'input_string'.

    var calculation = inputCalculation
    var expression = inputExpression

    // Currency Extension (standard library)
    if (USE_CURRENCY_EXTENSION)
        calculation = currencyExtension.currency(calculation, expression)

    /** Place third-party libraries below **/

    /** Place third-party libraries above **/

    return calculation
}

// Function to be called before displaying the result on the screen
fun extensionBfDisplaying(inputResult : String) : String {

    // Use the 'result' variables in the extensions.
    // If initial values are required, then use the static variable 'input_result'.
    var result = inputResult

    // Currency Extension (standard library)
    if (USE_CURRENCY_EXTENSION)
        result += currencyExtension.convertInto

    /** Place third-party libraries below **/

    /** Place third-party libraries above **/

    return result
}

// Function to be called before the string is processed
// It is necessary to prepare the string for processing by extensions
fun extensionSingleUse(inputExpression : String) : String {

    // Use the 'string' variables in the extensions.
    // If initial values are required, then use the static variable 'input_string'.
    var expression = inputExpression

    // Scales forms
    if (USE_SCALES)
        expression = scales.expandScales(expression)

    // Currency Extension (standard library)
    if (USE_CURRENCY_EXTENSION)
        currencyExtension.convertInto = ""

    // Variables Extension (standard library)
    if (USE_VARIABLES_EXTENSION)
        expression = variablesExtension.variablesHandler(expression)

    if (USE_ALOG_EXTENSION)
        expression = alogExtension.useLogic(expression)

    /** Place third-party libraries below **/

    /** Place third-party libraries above **/

    return expression
}

// The function required to update the values of extension variables
fun extensionUpdateValues() {
    // The function does not accept or return any values!

    // Variables Extension (standard library)
    if (USE_VARIABLES_EXTENSION)
        variablesExtension.clearAllVariables()

    /** Place third-party libraries below **/

    /** Place third-party libraries above **/
}

// The function runs once when the application starts.
fun extensionOnAppLaunched() {
    // The function does not accept or return any values!

    // Currency Extension (standard library)
    if (USE_CURRENCY_EXTENSION)
        currencyExtension.runExchange()
}