package com.example.contextmonitoringapp2

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

// Function to read CSV data from assets and convert it to a list of Float
fun readCsvFromAssets(context: Context, fileName: String): List<Float> {
    val inputStream = context.assets.open(fileName)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val floatList = mutableListOf<Float>()

    reader.forEachLine { line ->
        val value = line.toFloatOrNull()  // Convert each line to Float
        value?.let { floatList.add(it) }  // Only add valid floats to the list
    }

    reader.close()  // Close the reader
    return floatList
}
