package com.example.contextmonitoringapp2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SymptomLoggingActivity : AppCompatActivity() {

    private lateinit var recyclerSymptoms: RecyclerView
    private lateinit var uploadButton: Button
    private lateinit var healthDataSQLiteHelper: HealthDataSQLiteHelper

    private val symptoms = listOf(
        Symptom("Nausea"),
        Symptom("Headache"),
        Symptom("Diarrhea"),
        Symptom("Sore Throat"),
        Symptom("Fever"),
        Symptom("Muscle Ache"),
        Symptom("Loss of Smell or Taste"),
        Symptom("Cough"),
        Symptom("Shortness of Breath"),
        Symptom("Feeling Tired")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom_logging)

        // Initialize SQLiteHelper
        healthDataSQLiteHelper = HealthDataSQLiteHelper(this)

        // Retrieve heart rate and respiratory rate passed from MainActivity
        val heartRate = intent.getIntExtra("heartRate", 0)
        val respiratoryRate = intent.getIntExtra("respiratoryRate", 0)

        recyclerSymptoms = findViewById(R.id.recycler_symptoms)
        uploadButton = findViewById(R.id.button_upload_symptoms)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set up the RecyclerView with symptoms
        val symptomAdapter = SymptomAdapter(symptoms)
        recyclerSymptoms.layoutManager = LinearLayoutManager(this)
        recyclerSymptoms.adapter = symptomAdapter

        // Set up the upload button to handle saving data
        uploadButton.setOnClickListener {
            // Get the symptom ratings
            val symptomRatings = symptoms.map { symptom ->
                if (symptom.isSelected) symptom.rating else 0
            }

            // Create a HealthData object to insert into the database
            val healthData = HealthData(
                heartRate = heartRate,
                respiratoryRate = respiratoryRate,
                nauseaRating = symptomRatings[0],  // Nausea
                headacheRating = symptomRatings[1],  // Headache
                diarrheaRating = symptomRatings[2],  // Diarrhea
                soreThroatRating = symptomRatings[3],  // Sore Throat
                feverRating = symptomRatings[4],  // Fever
                muscleAcheRating = symptomRatings[5],  // Muscle Ache
                lossOfSmellTasteRating = symptomRatings[6],  // Loss of Smell or Taste
                coughRating = symptomRatings[7],  // Cough
                shortnessOfBreathRating = symptomRatings[8],  // Shortness of Breath
                feelingTiredRating = symptomRatings[9]   // Feeling Tired
            )

            // Insert the health data into the SQLite database
            val result = healthDataSQLiteHelper.insertHealthData(healthData)

            // Show a success or failure message
            if (result != -1L) {
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()

                // Fetch and log all stored health data
                val allHealthData = healthDataSQLiteHelper.getAllHealthData()
                for (data in allHealthData) {
                    Log.d("SymptomLoggingActivity", "HealthData: $data")
                }

            } else {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle back button click in the toolbar using OnBackPressedDispatcher
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()  // Use the OnBackPressedDispatcher to handle the back action
        return true
    }
}

