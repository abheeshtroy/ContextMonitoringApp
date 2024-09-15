package com.example.contextmonitoringapp2

data class HealthData(
    val id: Int = 0,
    val heartRate: Int,
    val respiratoryRate: Int,
    val nauseaRating: Int = 0,
    val headacheRating: Int = 0,
    val diarrheaRating: Int = 0,
    val soreThroatRating: Int = 0,
    val feverRating: Int = 0,
    val muscleAcheRating: Int = 0,
    val lossOfSmellTasteRating: Int = 0,
    val coughRating: Int = 0,
    val shortnessOfBreathRating: Int = 0,
    val feelingTiredRating: Int = 0
)