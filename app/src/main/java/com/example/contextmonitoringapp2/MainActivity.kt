package com.example.contextmonitoringapp2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    // UI components
    private val STORAGE_PERMISSION_CODE = 101
    lateinit var heartRateButton: Button
    lateinit var heartRateTextView: TextView
    lateinit var respiratoryRateButton: Button
    lateinit var respiratoryRateTextView: TextView

    // Variables to store calculated heart rate and respiratory rate
    private var heartRate: Int = 0
    private var respiratoryRate: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize UI components
        heartRateButton = findViewById(R.id.button_measure_heart_rate)
        heartRateTextView = findViewById(R.id.tv_heart_rate_value)

        respiratoryRateButton = findViewById(R.id.button_measure_respiratory_rate)
        respiratoryRateTextView = findViewById(R.id.tv_respiratory_rate_value)


        // Check for media permission (for reading the video)
        checkMediaPermission()

        // Set up the button to measure heart rate
        heartRateButton.setOnClickListener {
            measureHeartRate()
        }

        // Set up button click listener to calculate respiratory rate
        respiratoryRateButton.setOnClickListener {
            calculateRespiratoryRate()
        }

        // Initialize the symptoms button
        val symptomsButton = findViewById<Button>(R.id.button_symptoms)

        // Set up a click listener on the symptoms button to navigate to SymptomLoggingActivity
        symptomsButton.setOnClickListener {
            // Create an Intent to navigate to SymptomLoggingActivity and pass the heart rate and respiratory rate
            val intent = Intent(this, SymptomLoggingActivity::class.java)
            intent.putExtra("heartRate", heartRate)  // Pass the calculated heart rate
            intent.putExtra("respiratoryRate", respiratoryRate)  // Pass the calculated respiratory rate
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to measure heart rate
    private fun measureHeartRate() {
        // Path to the video file stored in the emulator's Downloads folder
        val videoPath = "/storage/emulated/0/Download/Heart_Rate.mp4"
        val videoUri = Uri.parse(videoPath)

        // Use coroutines to calculate heart rate
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Call the heartRateCalculator with just the URI
                heartRate = heartRateCalculator(videoUri) // Store the heart rate in the global variable
                heartRateTextView.text = "Heart Rate: $heartRate"
            } catch (e: Exception) {
                Log.e("MainActivity", "Error measuring heart rate", e)
                heartRateTextView.text = "Error: ${e.localizedMessage}"
            }
        }
    }

    // Function to check for permission to read media (specifically video files)
    private fun checkMediaPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO)
            != PackageManager.PERMISSION_GRANTED) {

            // Request permission if it's not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_VIDEO),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed with reading the file
        } else {
            // Permission denied, show an error or disable functionality
            heartRateTextView.text = "Permission denied"
        }
    }

    private fun calculateRespiratoryRate() {
        // Read the 3 CSV files for X, Y, and Z axis data from assets
        val accelValuesX = readCsvFromAssets(this, "CSVBreatheX.csv").toMutableList()
        val accelValuesY = readCsvFromAssets(this, "CSVBreatheY.csv").toMutableList()
        val accelValuesZ = readCsvFromAssets(this, "CSVBreatheZ.csv").toMutableList()

        // Call the respiratory rate calculator helper function
        respiratoryRate = respiratoryRateCalculator(accelValuesX, accelValuesY, accelValuesZ)

        // Display the calculated respiratory rate on the UI
        respiratoryRateTextView.text = "Respiratory Rate: $respiratoryRate"
    }
}