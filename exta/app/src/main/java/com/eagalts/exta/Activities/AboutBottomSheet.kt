package com.eagalts.exta.Activities

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.eagalts.exta.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AboutBottomSheet : BottomSheetDialogFragment() {
    fun rerunActivity() {
        (activity as? MainActivity)?.run()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val messageBtn = view.findViewById<Button>(R.id.message_btn)

        messageBtn.setOnClickListener {
            val width = Resources.getSystem().displayMetrics.widthPixels
            val height = Resources.getSystem().displayMetrics.heightPixels

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("exta_mobile@inbox.ru"))
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "${getString(R.string.app_name)} | ${getString(R.string.email_header)}"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                            "** ${getString(R.string.email_info)} **\n\n" +
                            " - ${getString(R.string.email_currentVersion)} ${getString(R.string.app_name)}: ${getString(R.string.version)}\n" +
                            " - ${getString(R.string.email_device)}: ${android.os.Build.MANUFACTURER} '${android.os.Build.MODEL}'\n" +
                            " - ${getString(R.string.email_sdk)}: ${android.os.Build.VERSION.SDK_INT} [${android.os.Build.DISPLAY}]\n" +
                            " - ${getString(R.string.email_screenRes)}: ${width}x${height}\n" +
                            " - ${getString(R.string.email_hw)}: ${android.os.Build.HARDWARE}" +
                                " | ${getString(R.string.email_pr)}: ${android.os.Build.PRODUCT}" +
                                " | ${getString(R.string.email_br)}: ${android.os.Build.BOARD}\n\n" +
                            "------------\n" +
                            "${getString(R.string.email_greetings)}\n\n"

                )
            }

            startActivity(emailIntent)
        }

        return view
    }
}