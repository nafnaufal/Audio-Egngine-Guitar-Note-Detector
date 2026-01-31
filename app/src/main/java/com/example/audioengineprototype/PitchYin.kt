package com.example.audioengineprototype

import kotlin.math.abs

fun detectPitchYin(
    buffer: FloatArray,
    sampleRate: Int,
    threshold: Float = 0.15f
): Float {
    val size = buffer.size
    val half = size / 2
    val diff = FloatArray(half)

    // 1) difference function
    for (tau in 1 until half) {
        var sum = 0f
        for (i in 0 until half) {
            val d = buffer[i] - buffer[i + tau]
            sum += d * d
        }
        diff[tau] = sum
    }

    // 2) cumulative mean normalized difference
    var runningSum = 0f
    diff[0] = 1f
    for (tau in 1 until half) {
        runningSum += diff[tau]
        diff[tau] = diff[tau] * tau / runningSum
    }

    // 3) absolute threshold
    for (tau in 2 until half) {
        if (diff[tau] < threshold) {
            // 4) local minimum
            var t = tau
            while (t + 1 < half && diff[t + 1] < diff[t]) t++
            return sampleRate.toFloat() / t
        }
    }

    return -1f
}
