package com.eagalts.exta.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.eagalts.exta.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val faq = findViewById<Button>(R.id.faq_button)
        val go = findViewById<FloatingActionButton>(R.id.go_button)

        faq.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://eagalts.gitbook.io/exta/russian/vremya"))
            startActivity(browserIntent)
        }

        go.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            this.finish()
        }

    }
}