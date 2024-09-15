package com.example.contextmonitoringapp2

data class Symptom(
    val name: String,
    var isSelected: Boolean = false,
    var rating: Int = 0
)