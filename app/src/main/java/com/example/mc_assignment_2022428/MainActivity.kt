package com.example.mc_assignment_2022428

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sourceEditText = findViewById<EditText>(R.id.sourceEditText)
        val destinationEditText = findViewById<EditText>(R.id.destinationEditText)
        val startJourneyButton = findViewById<Button>(R.id.startJourneyButton)

        startJourneyButton.setOnClickListener {
            val source = sourceEditText.text.toString()
            val destination = destinationEditText.text.toString()


            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("SOURCE", source)
            intent.putExtra("DESTINATION", destination)
            startActivity(intent)
        }
    }
}


