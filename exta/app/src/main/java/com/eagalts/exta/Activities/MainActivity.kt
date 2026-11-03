package com.eagalts.exta.Activities

/// ====================================================================================
///     Default Android imports
/// ====================================================================================
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
/// ====================================================================================
///     Android | Snackbar
/// ====================================================================================
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
/// ====================================================================================
///     Android | Vibrations
/// ====================================================================================
import android.os.VibrationEffect
import android.os.Vibrator
/// ====================================================================================
///     Android | Widgets
/// ====================================================================================
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast.LENGTH_SHORT
/// ====================================================================================
///     exta imports
/// ====================================================================================
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.eagalts.exta.R
/// ====================================================================================
///     exta | Aliases
/// ====================================================================================
import com.eagalts.exta.aliasesExpression
import com.eagalts.exta.aliasesRussian
import com.eagalts.exta.aliasesString
import com.eagalts.exta.timeAliases
/// ====================================================================================
///     exta | Local params
/// ====================================================================================
import com.eagalts.exta.Extensions.USE_DEGREES_IN_CALCULATION
import com.eagalts.exta.Extensions.USE_TIME_MATH
import com.eagalts.exta.Extensions.USE_SAVES
/// ====================================================================================
///     exta | Cache
/// ====================================================================================
import com.eagalts.exta.Extensions.Cache.FirstLaunch
import com.eagalts.exta.Extensions.Cache.SaveLoadNote
import com.eagalts.exta.Extensions.Cache.Settings
/// ====================================================================================
///     exta | Extensions
/// ====================================================================================
import com.eagalts.exta.Extensions.ConvertRadiansExtension.processInputString
import com.eagalts.exta.Extensions.TimeMathExtension.TimeMathExtension
import com.eagalts.exta.Extensions.formatNotepadStyle
import com.eagalts.exta.Extensions.extaAPI
import com.eagalts.exta.Extensions.extensionBfDisplaying
import com.eagalts.exta.Extensions.extensionExec
import com.eagalts.exta.Extensions.extensionOnAppLaunched
import com.eagalts.exta.Extensions.extensionSingleUse
import com.eagalts.exta.Extensions.extensionUpdateValues
/// ====================================================================================
///     Firebase
/// ====================================================================================
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
/// ====================================================================================
///     Math expression solver
/// ====================================================================================
import org.mariuszgromada.math.mxparser.Expression
import java.math.RoundingMode
import java.text.DecimalFormat



@SuppressLint("StaticFieldLeak")
lateinit var CONTEXT : Context

const val AppName = "exta"
var isFirstLaunched = false

private lateinit var firebaseAnalytics: FirebaseAnalytics

class MainActivity : AppCompatActivity() {
    private lateinit var vibrator: Vibrator

    private lateinit var textWatcher: TextWatcher

    private fun showSimpleSnackbar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(" ") { }


        val snackbarView = snackbar.view
        snackbarView.setBackgroundResource(R.drawable.snack)
        snackbar.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        /// ====================================================================================
        ///     Initializing an Activity
        /// ====================================================================================

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CONTEXT = this@MainActivity

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics

        /// ====================================================================================
        ///     Setting flags
        /// ====================================================================================

        val firstAppLaunchFlag = FirstLaunch(CONTEXT)
        val savingAndLoading = SaveLoadNote(CONTEXT)

        /// ====================================================================================
        ///     Initializing a local settings
        /// ====================================================================================

        // Initializing local settings storage
        val settings = Settings(CONTEXT)

        // Getting the latest running version of the application
        // Need to be able to display a screen with new features after upgrading to a new version
        val lastVersionLaunched = settings.get("lastVersionLaunched")

        // Getting the current version of the application
        val nowVersion = resources.getString(R.string.version)

        // Initialization of intent.extras
        val arguments = intent.extras

        /// ====================================================================================
        ///     Retrieving parameters from local storage
        /// ====================================================================================

        USE_DEGREES_IN_CALCULATION = settings.get("angle").toBoolean()
        USE_TIME_MATH = settings.get("timemath").toBoolean()

        // Obtaining local information about the save option
        USE_SAVES = settings.get("saves").toBoolean()

        /// ====================================================================================
        ///     Initializing a local variables
        /// ====================================================================================

        var backupTextBlock : String = ""
        var backupResultsBlock : String = ""

        /// ====================================================================================
        ///     Getting element instances on Activity
        /// ====================================================================================

        // Notepad
        val textBlock = findViewById<EditText>(R.id.textBlock)
        val resultsBlock = findViewById<TextView>(R.id.resultsBlock)

        // Top-bar
        val settingsButton = findViewById<Button>(R.id.settingsBtn)
        val appNamePlain = findViewById<TextView>(R.id.appNamePlain)
        val eraseButton = findViewById<Button>(R.id.eraseBtn)

        /// ====================================================================================
        ///     Initializing a local settings
        /// ====================================================================================

        // Initializing settings at first startup
        // The default settings are set with all extensions enabled

        // First application launch BEFORE calling GreetingsActivity
        if (!firstAppLaunchFlag.get()) {
            // Accuracy (number of decimal places) - 2 decimal places by default
            settings.set("accuracy", "#.##")

            // Default value
            val value = true

            // Setting parameters to default 'value'
            settings.set("variables", value.toString())
            settings.set("currency", value.toString())
            settings.set("saves", value.toString())
            settings.set("scales", value.toString())
            settings.set("timemath", value.toString())
            settings.set("angle", false.toString())
            settings.set("lastVersionLaunched", nowVersion)

            // Launching the welcome screen 'GreetingsActivity'
            val intent = Intent(this, GreetingsActivity::class.java)
            startActivity(intent)

            // Closing the current screen
            this.finish()
        }

        // First application launch AFTER calling GreetingsActivity
        else if (isFirstLaunched) {
            isFirstLaunched = false
            textBlock.setText("# ${getString(R.string.welcome_line1)}\n\n# ${getString(R.string.welcome_line2)}\n# ${getString(R.string.welcome_line3)}\n# ${getString(R.string.welcome_line4)}\n\n6 × 4\n5³ ÷ 25\n\nN = 500 + 10\n10% ${getString(R.string.welcome_line5)} N\nN × 3 + 470\n\n${getString(R.string.welcome_line6)}\n${getString(R.string.welcome_line7)}\n\n12:30 + 7:45\n${getString(R.string.welcome_line8)}")
            run()
        }

        // Launching the 'UpdateActivity' screen after an application update
        else if (lastVersionLaunched != nowVersion
            && lastVersionLaunched != "2.1.0"
            && lastVersionLaunched != "2.1.1"
            && lastVersionLaunched != "2.1.2"
        ) {
            val intent = Intent(this, UpdateActivity::class.java)
            startActivity(intent)

            // Default value
            val value = true

            // Setting new parameters to default 'value'
            settings.set("scales", value.toString())
            settings.set("timemath", value.toString())
            settings.set("angle", false.toString())

            // Updating the latest running version in local storage
            settings.set("lastVersionLaunched", nowVersion)
        }

        /// ====================================================================================
        ///
        ///     Actions to be performed at application startup (every time)
        ///
        /// ====================================================================================

        extensionOnAppLaunched()

        /// ====================================================================================
        ///     Getting the latest available version via extaAPI
        /// ====================================================================================

        extaAPI.getVersionFromAPI()

        if (!settings.get("OutdatedVersion").toBoolean())
            extaAPI.getVersionFromAPI()
        else
            extaAPI.version = settings.get("LatestVersion")

        /// ====================================================================================
        ///     Local saving and loading of notepad on startup
        /// ====================================================================================

        // Loading a locally saved Notepad when the save option is enabled
        if (USE_SAVES) {
            textBlock.setText(savingAndLoading.readBlock())
            resultsBlock.text = savingAndLoading.readResults()
        }

        if (arguments != null)
            if (arguments["needUpdate"].toString().toBoolean())
                run()

        /// ====================================================================================
        ///     Initializing the vibration response
        /// ====================================================================================

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        /// ====================================================================================
        ///     Customizing the actions performed when the buttons are pressed
        /// ====================================================================================

        var erasedBefore by mutableStateOf(false)

        settingsButton.setOnClickListener {
            val settingsBottomSheet = SettingsBottomSheet()
            settingsBottomSheet.show(supportFragmentManager, "SettingsBottomSheet")

            vibrator.vibrate(VibrationEffect.createOneShot(1, VibrationEffect.DEFAULT_AMPLITUDE))
        }

        appNamePlain.setOnLongClickListener {
            val aboutBottomSheet = AboutBottomSheet()
            aboutBottomSheet.show(supportFragmentManager, "AboutBottomSheet")

            vibrator.vibrate(VibrationEffect.createOneShot(1, VibrationEffect.EFFECT_HEAVY_CLICK))

            true
        }

        eraseButton.setOnClickListener {
            if (resultsBlock.text != "") {
                backupTextBlock = textBlock.text.toString()
                backupResultsBlock = resultsBlock.text.toString()

                textBlock.setText("")
                resultsBlock.text = ""
            }

            else if (resultsBlock.text == "" && backupTextBlock != "") {
                textBlock.setText(backupTextBlock)
                resultsBlock.text = backupResultsBlock

                backupTextBlock = ""
                backupResultsBlock = ""
            }


            if (!erasedBefore) {
                Toast
                    .makeText(
                        this,
                        getString(R.string.toast_returnNotebook),
                        LENGTH_SHORT
                    )
                    .show()
                
                erasedBefore = true
            }


            vibrator.vibrate(VibrationEffect.createOneShot(6, VibrationEffect.DEFAULT_AMPLITUDE))
        }


        /// ====================================================================================
        ///
        ///     Notepad
        ///
        /// ====================================================================================

        // Setting TextWatcher (main notepad block)
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                formatNotepadStyle(textBlock, textWatcher)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { run() }
        }

        // Setting TextWatcher to EditText
        textBlock.addTextChangedListener(textWatcher)

        // Apply formatting at application startup
        formatNotepadStyle(textBlock, textWatcher)
    }



    /// ========================================================================================
    ///     function run()
    /// ========================================================================================

    /**
     * A function called each time the notepad is updated by the user.
     *
     * It performs formatting and also performs calculations of user-specified commands.
     */

    @SuppressLint("SetTextI18n")
    fun run() {
        /// ====================================================================================
        ///     Getting element instances on Activity
        /// ====================================================================================
        val inputTextBlock = findViewById<EditText>(R.id.textBlock)
        val resultsBlock = findViewById<TextView>(R.id.resultsBlock)

        /// ====================================================================================
        ///     Initializing a local settings
        /// ====================================================================================
        val settings = Settings(this@MainActivity)
        val saveLoadNote = SaveLoadNote(this@MainActivity)

        // Obtaining local information about the save option
        USE_SAVES = settings.get("saves").toBoolean()

        // Cache initialization (using HashMap)
        val calculationCache = mutableMapOf<String, String>()

        /// ====================================================================================
        ///     Initializing a local variables
        /// ====================================================================================
        val sourceText = inputTextBlock.text.toString()
        val inlineSourceText = sourceText.split('\n')

        /// ====================================================================================
        ///     Initializing extensions
        /// ====================================================================================

        // TimeMathExtension using
        val timeMathExtension = TimeMathExtension()

        extensionUpdateValues()

        /// ====================================================================================
        ///     Calculating
        /// ====================================================================================

        resultsBlock.text = ""

        for (i in inlineSourceText.indices) {

            /// ================================================================================
            ///     Preparing
            /// ================================================================================
            var string = inlineSourceText[i].lowercase()
            string = extensionSingleUse(string)

            /// ================================================================================
            ///     Replacing aliases
            /// ================================================================================

            for (alias in aliasesString)
                string = string.replace(alias.key, alias.value)

            var expression: String = string

            for (alias in aliasesExpression)
                expression = expression.replace(alias.key, alias.value)

            for (alias in aliasesRussian)
                expression = expression.replace(alias.key, alias.value)

            if (USE_TIME_MATH)
                for (alias in timeAliases)
                    expression = expression.replace(alias.key, alias.value)

            /// ================================================================================
            ///     Setting local variables (needed to perform calculations)
            /// ================================================================================

            var calculationResult : Double = Double.NaN
            lateinit var r: String
            var timeMathResult = "null"

            /// ================================================================================
            ///     Check if the result is already in the cache
            /// ================================================================================

            val cachedResult = calculationCache[expression]
            if (cachedResult != null) {
                resultsBlock.text = "${resultsBlock.text}$cachedResult\n"
                continue
            }

            /// ================================================================================
            ///     Check whether the request is time-related (separate processing)
            /// ================================================================================

            if (expression != "" && expression[0] != '#' && USE_TIME_MATH)
                timeMathResult = timeMathExtension.runExtension(CONTEXT, expression).toString()

            /// ================================================================================
            ///     Performing a query calculation
            /// ================================================================================

            // Executed provided that the current string is non-empty,
            // is not time-related, and contains a math query

            if (timeMathResult.isEmpty() || timeMathResult == "null") {

                // If the string is not a comment and is not empty, perform calculations
                if (expression != "" && expression[0] != '#' && expression.any { it.isDigit() }) {
                    expression = processInputString(expression, USE_DEGREES_IN_CALCULATION)

                    calculationResult = Expression(expression).calculate()

                    calculationResult = extensionExec(calculationResult, string)
                }

                // If in the course of calculations we didn't get a non-number,
                // we process it separately
                if (calculationResult.toString() != "NaN") {

                    // If the fractional part can be discarded from the obtained number and the
                    // number does not change, then the result is an integer
                    // (discard the fractional part).

                    if (calculationResult.toInt().toDouble() == calculationResult)
                        r = calculationResult.toInt().toString()

                    // If the fractional part cannot be discarded from the obtained number
                    // (the number changes), format its output according
                    // to the application settings.

                    else {
                        val df = DecimalFormat(settings.get("accuracy"))
                        df.roundingMode = RoundingMode.DOWN
                        r = df.format(calculationResult).toString()
                    }

                    // Running extensions before displaying the result on the screen
                    r = extensionBfDisplaying(r)

                    // Displaying the result on the screen
                    resultsBlock.text = "${resultsBlock.text}${r}\n"

                    // Save the result to the cache
                    calculationCache[expression] = r
                }

                // If in the course of calculations we get a non-number,
                // we just ignore this.

                else {
                    resultsBlock.text = "${resultsBlock.text}\n"
                }
            }

            // If the string is time-related,
            // then the previously obtained result is displayed

            else {
                resultsBlock.text = "${resultsBlock.text}${timeMathResult}\n"
            }

            /// ================================================================================
            ///     Save Notepad locally (if the save option is enabled)
            /// ================================================================================

            if (USE_SAVES) {
                saveLoadNote.saveAll(sourceText, resultsBlock.text.toString())
            }
        }
    }

    companion object {
        val extaAPI = extaAPI()
    }
}