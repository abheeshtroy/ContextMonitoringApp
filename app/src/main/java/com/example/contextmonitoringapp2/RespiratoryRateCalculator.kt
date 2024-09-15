package com.example.contextmonitoringapp2

import kotlin.math.pow

fun respiratoryRateCalculator(
    accelValuesX: MutableList<Float>,
    accelValuesY: MutableList<Float>,
    accelValuesZ: MutableList<Float>
): Int {
    var previousValue: Float = 10f
    var currentValue: Float
    var k = 0

    for (i in 11 until accelValuesY.size) {
        currentValue = kotlin.math.sqrt(
            accelValuesZ[i].toDouble().pow(2.0) +
                    accelValuesX[i].toDouble().pow(2.0) +
                    accelValuesY[i].toDouble().pow(2.0)
        ).toFloat()

        if (kotlin.math.abs(previousValue - currentValue) > 0.15) {
            k++
        }
        previousValue = currentValue
    }

    val ret = (k.toDouble() / 45.00)
    return (ret * 30).toInt()  // Returns respiratory rate in breaths per minute
}
