package com.eagalts.exta.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.eagalts.exta.Extensions.Cache.Settings
import com.eagalts.exta.Extensions.USE_CURRENCY_EXTENSION
import com.eagalts.exta.Extensions.USE_DEGREES_IN_CALCULATION
import com.eagalts.exta.Extensions.USE_SAVES
import com.eagalts.exta.Extensions.USE_SCALES
import com.eagalts.exta.Extensions.USE_TIME_MATH
import com.eagalts.exta.Extensions.USE_VARIABLES_EXTENSION
import com.eagalts.exta.Extensions.currencyExtension
import com.eagalts.exta.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsBottomSheet : BottomSheetDialogFragment() {
    fun rerunActivity() {
        (activity as? MainActivity)?.run()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val settings = Settings(requireContext())

        val accuracy = view.findViewById<EditText>(R.id.accuracy)
        val currency = view.findViewById<CheckBox>(R.id.currency)
        val vars = view.findViewById<CheckBox>(R.id.vars)
        val scales = view.findViewById<CheckBox>(R.id.scalesBox)
        val timemath = view.findViewById<CheckBox>(R.id.timeMathBox)
        val saves = view.findViewById<CheckBox>(R.id.saveOnLeave)
        val faq = view.findViewById<Button>(R.id.faq_btn)
        val version = view.findViewById<TextView>(R.id.version_line)
        val info_message = view.findViewById<TextView>(R.id.info_message)
        val warn_message = view.findViewById<TextView>(R.id.warn_message)

        val extaAPI = MainActivity.extaAPI

        if (extaAPI.version != getString(R.string.version) && extaAPI.version.isNotEmpty()) {
            settings.set("OutdatedVersion", "true")
            settings.set("LatestVersion", extaAPI.version)
            info_message.visibility = View.VISIBLE
        }

        if (currencyExtension.errorCode) {
            warn_message.visibility = View.VISIBLE
            warn_message.text = "${getString(R.string.err_connection_line1)}\n" +
                    getString(R.string.err_connection_line2)
        }

        /// ====================================================================================
        ///     Initializing the vibration response
        /// ====================================================================================

        version.text = "${getString(R.string.settings_more)}"

        version.setOnClickListener {
            val aboutBottomSheet = AboutBottomSheet()
            aboutBottomSheet.show(parentFragmentManager, "AboutBottomSheet")
        }

        val accuracyCount = settings.get("accuracy").length - 2
        accuracy.setText(accuracyCount.toString())

        accuracy.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (accuracy.text.isNotEmpty()) {
                    try {
                        val count = accuracy.text.toString().toInt()
                        if (count != 0) {
                            val accuracyTemplate = "#." + "#".repeat(count)
                            settings.set("accuracy", accuracyTemplate)
                        }
                    } catch (e: Exception) {
                        Log.e(AppName, "Error while trying to change precision!")
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        faq.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://eagalts.gitbook.io/exta/"))
            startActivity(browserIntent)
        }

        currency.isChecked = getCurrencyExtension()
        currency.setOnCheckedChangeListener { _, isChecked -> updateCurrencyExtension(isChecked) }

        vars.isChecked = getVariablesExtension()
        vars.setOnCheckedChangeListener { _, isChecked -> updateVariablesExtension(isChecked) }

        scales.isChecked = getScaleSupportOption()
        scales.setOnCheckedChangeListener { _, isChecked -> updateScaleSupportOption(isChecked) }

        timemath.isChecked = getTimeMathSupportOption()
        timemath.setOnCheckedChangeListener { _, isChecked -> updateTimeMathSupportOption(isChecked) }

        saves.isChecked = getSavingOption()
        saves.setOnCheckedChangeListener { _, isChecked -> updateSavingOption(isChecked) }

        val switchModeCalculation = view.findViewById<Switch>(R.id.switchModeCalculation)
        val switchModeText = view.findViewById<TextView>(R.id.switchModeCalculation)
        val degTextLayout = view.findViewById<TextView>(R.id.DegTextLayout)

        fun typefaceAngleMode(rad: Boolean = false, deg: Boolean = false) {
            if (rad) {
                switchModeText.setTypeface(null, Typeface.BOLD)
                degTextLayout.setTypeface(null, Typeface.NORMAL)
            }
            else if (deg) {
                switchModeText.setTypeface(null, Typeface.NORMAL)
                degTextLayout.setTypeface(null, Typeface.BOLD)
            }
        }

        switchModeCalculation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateAngleSetting(true)
                typefaceAngleMode(deg = true)

                rerunActivity()
            } else {
                updateAngleSetting(false)
                typefaceAngleMode(rad = true)

                rerunActivity()
            }
        }

        switchModeCalculation.isChecked = getAngleSetting()

        if (USE_DEGREES_IN_CALCULATION) typefaceAngleMode(deg = true)
        else typefaceAngleMode(rad = true)

        return view
    }

    // ==========================================================
    //  CURRENCY
    // ==========================================================

    private fun updateCurrencyExtension(value: Boolean) {
        USE_CURRENCY_EXTENSION = value
        val settings = Settings(requireContext())
        settings.set("currency", value.toString())

        rerunActivity()
    }

    private fun getCurrencyExtension(): Boolean {
        val settings = Settings(requireContext())
        USE_CURRENCY_EXTENSION = settings.get("currency").toBoolean()
        return USE_CURRENCY_EXTENSION
    }

    // ==========================================================
    //  VARIABLES
    // ==========================================================

    private fun updateVariablesExtension(value: Boolean) {
        USE_VARIABLES_EXTENSION = value
        val settings = Settings(requireContext())
        settings.set("variables", value.toString())

        rerunActivity()
    }

    private fun getVariablesExtension(): Boolean {
        val settings = Settings(requireContext())
        USE_VARIABLES_EXTENSION = settings.get("variables").toBoolean()
        return USE_VARIABLES_EXTENSION
    }

    // ==========================================================
    //  SAVE NOTEPAD OPTION
    // ==========================================================

    private fun updateSavingOption(value: Boolean) {
        USE_SAVES = value
        val settings = Settings(requireContext())
        settings.set("saves", value.toString())

        rerunActivity()
    }

    private fun getSavingOption(): Boolean {
        val settings = Settings(requireContext())
        USE_SAVES = settings.get("saves").toBoolean()
        return USE_SAVES
    }

    // ==========================================================
    //  SCALES
    // ==========================================================

    private fun updateScaleSupportOption(value: Boolean) {
        USE_SCALES = value
        val settings = Settings(requireContext())
        settings.set("scales", value.toString())

        rerunActivity()
    }

    private fun getScaleSupportOption(): Boolean {
        val settings = Settings(requireContext())
        USE_SCALES = settings.get("scales").toBoolean()
        return USE_SCALES
    }

    // ==========================================================
    //  TIME MATH OPERATIONS
    // ==========================================================

    private fun updateTimeMathSupportOption(value: Boolean) {
        USE_TIME_MATH = value
        val settings = Settings(requireContext())
        settings.set("timemath", value.toString())

        rerunActivity()
    }

    private fun getTimeMathSupportOption(): Boolean {
        val settings = Settings(requireContext())
        USE_TIME_MATH = settings.get("timemath").toBoolean()
        return USE_TIME_MATH
    }

    // ==========================================================
    //  ANGLES
    // ==========================================================

    private fun updateAngleSetting(value: Boolean) {
        USE_DEGREES_IN_CALCULATION = value
        val settings = Settings(requireContext())
        settings.set("angle", value.toString())
    }

    private fun getAngleSetting(): Boolean {
        val settings = Settings(requireContext())
        USE_DEGREES_IN_CALCULATION = settings.get("angle").toBoolean()
        return USE_DEGREES_IN_CALCULATION
    }
}
