package com.eagalts.exta.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.eagalts.exta.R
import com.eagalts.exta.Extensions.Cache.FirstLaunch
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GreetingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greetings)

        val faq = findViewById<Button>(R.id.faq_button)

        faq.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://eagalts.gitbook.io/exta/"))
            startActivity(browserIntent)
        }

        val go = findViewById<FloatingActionButton>(R.id.go_button)

        go.setOnClickListener {
            val firstLaunch = FirstLaunch(this@GreetingsActivity)
            firstLaunch.set()

            isFirstLaunched = true

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            this.finish()
        }
    }
}