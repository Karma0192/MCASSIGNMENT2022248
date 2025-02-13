package com.example.mc_assignment_2022428

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private var currentStopIndex = 0
    private val stops = listOf(
        Stop("Stop 1", 500.0, true, "2 hours"),
        Stop("Stop 2", 300.0, false, "1.5 hours"),
        Stop("Stop 3", 200.0, true, "1 hour"),
        Stop("Stop 4", 400.0, false, "2 hours")
    )
    private var isKilometers = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val source = intent.getStringExtra("SOURCE")
        val destination = intent.getStringExtra("DESTINATION")

        // Finding UI elements
        val sourceAndDestinationText = findViewById<TextView>(R.id.sourceAndDestinationText)
        val journeyDetailsText = findViewById<TextView>(R.id.journeyDetailsText)
        val visaRequirementText = findViewById<TextView>(R.id.visaRequirementText)
        val distanceText = findViewById<TextView>(R.id.distanceText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val switchUnitButton = findViewById<Button>(R.id.switchUnitButton)
        val nextStopButton = findViewById<Button>(R.id.nextStopButton)


        sourceAndDestinationText.text = "Journey from $source to $destination"

        updateJourneyDetails(journeyDetailsText, visaRequirementText, distanceText, progressBar)


        switchUnitButton.setOnClickListener {
            isKilometers = !isKilometers
            updateJourneyDetails(journeyDetailsText, visaRequirementText, distanceText, progressBar)
        }


        nextStopButton.setOnClickListener {
            if (currentStopIndex < stops.size - 1) {
                currentStopIndex++
                updateJourneyDetails(journeyDetailsText, visaRequirementText, distanceText, progressBar)
            }
        }
    }

    private fun updateJourneyDetails(
        journeyDetailsText: TextView,
        visaRequirementText: TextView,
        distanceText: TextView,
        progressBar: ProgressBar
    ) {
        val stop = stops[currentStopIndex]
        val unit = if (isKilometers) "km" else "miles"
        val distance = if (isKilometers) stop.distance else stop.distance * 0.621371

        journeyDetailsText.text = "Journey in Progress"
        visaRequirementText.text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}"
        distanceText.text = "Distance to Next Stop: $distance $unit"

        val totalDistance = stops.sumOf { it.distance }
        val distanceCovered = stops.take(currentStopIndex + 1).sumOf { it.distance }
        val progress = (distanceCovered / totalDistance * 100).toInt()
        progressBar.progress = progress
    }
}

data class Stop(val name: String, val distance: Double, val visaRequired: Boolean, val time: String)


