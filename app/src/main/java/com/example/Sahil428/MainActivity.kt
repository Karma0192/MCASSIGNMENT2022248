package com.example.Sahil428

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class JourneyLeg(val from: String, val to: String, val distance: Double)


val countryOrder = listOf("Japan", "Korea", "China", "India", "Singapore")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JourneyStartScreen()
        }
    }
}

@Composable
fun JourneyStartScreen() {

    var sourceCountry by remember { mutableStateOf("Japan") }
    var destinationCountry by remember { mutableStateOf("India") }

    var isKilometers by remember { mutableStateOf(true) }

    var currentLegIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current


    val route = computeRoute(countryOrder, sourceCountry, destinationCountry)

    val journeyLegs = route.zipWithNext().map { (from, to) ->
        JourneyLeg(from, to, getDistanceBetween(from, to))
    }

    val totalDistance = journeyLegs.sumOf { it.distance }

    val distanceCovered = journeyLegs.take(currentLegIndex).sumOf { it.distance }
    val distanceLeft = totalDistance - distanceCovered
    val progressFraction = if (totalDistance > 0) (distanceCovered / totalDistance).toFloat() else 0f


    val currentLocation = if (route.isNotEmpty()) route[currentLegIndex] else ""

    val visaNeeded = if (getVisaRequirement(currentLocation)) "Yes" else "No"

    val convert: (Double) -> Double = { if (isKilometers) it else it * 0.621371 }
    val unit = if (isKilometers) "km" else "miles"

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownMenuComponent(
                options = countryOrder,
                selectedItem = sourceCountry,
                onItemSelected = {
                    sourceCountry = it

                    currentLegIndex = 0
                }
            )
            DropdownMenuComponent(
                options = countryOrder,
                selectedItem = destinationCountry,
                onItemSelected = {
                    destinationCountry = it
                    currentLegIndex = 0
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Journey from $sourceCountry to $destinationCountry",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Route Table", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Route", modifier = Modifier.weight(1f), fontSize = 16.sp, color = Color.Blue)
            Text(text = "Distance", modifier = Modifier.weight(1f), fontSize = 16.sp, color = Color.Blue)
        }
        Divider(color = Color.Gray, thickness = 1.dp)

        journeyLegs.forEach { leg ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Text(text = "${leg.from} → ${leg.to}", modifier = Modifier.weight(1f))
                val displayDistance = convert(leg.distance).toInt()
                Text(text = "$displayDistance $unit", modifier = Modifier.weight(1f))
            }
            Divider(color = Color.LightGray, thickness = 0.5.dp)
        }
        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Current Location: $currentLocation", fontSize = 18.sp)
        Text(text = "Visa Required: $visaNeeded", fontSize = 18.sp)
        Text(
            text = "Distance Covered: ${convert(distanceCovered).toInt()} $unit",
            fontSize = 18.sp
        )
        Text(
            text = "Distance Left: ${convert(distanceLeft).toInt()} $unit",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))


        LinearProgressIndicator(progress = progressFraction, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { isKilometers = !isKilometers },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Switch to ${if (isKilometers) "Miles" else "Kilometers"}")
        }
        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                if (currentLegIndex < route.size - 1) {
                    currentLegIndex++
                } else {
                    Toast.makeText(context, "Journey Completed!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Next Stop Reached")
        }
    }
}


fun computeRoute(order: List<String>, from: String, to: String): List<String> {
    val fromIndex = order.indexOf(from)
    val toIndex = order.indexOf(to)
    return if (fromIndex == -1 || toIndex == -1) {
        emptyList()
    } else if (fromIndex <= toIndex) {
        order.subList(fromIndex, toIndex + 1)
    } else {
        order.subList(toIndex, fromIndex + 1).reversed()
    }
}


fun getDistanceBetween(from: String, to: String): Double {
    return when (setOf(from, to)) {
        setOf("Japan", "Korea") -> 800.0
        setOf("Korea", "China") -> 1200.0
        setOf("China", "India") -> 1500.0
        setOf("India", "Singapore") -> 2000.0
        else -> 0.0
    }
}


fun getVisaRequirement(country: String): Boolean {
    return when (country) {
        "Japan" -> false
        "Korea" -> false
        "China" -> true
        "India" -> true
        "Singapore" -> false
        else -> false
    }
}

@Composable
fun DropdownMenuComponent(
    options: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize()) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.width(140.dp)
        ) {
            Text(text = selectedItem.ifEmpty { "Select" }, fontSize = 14.sp)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onItemSelected(option)
                    expanded = false
                }) {
                    Text(text = option, fontSize = 14.sp)
                }
            }
        }
    }
}
