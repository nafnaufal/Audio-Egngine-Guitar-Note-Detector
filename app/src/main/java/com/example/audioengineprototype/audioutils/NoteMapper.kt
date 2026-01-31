package com.example.audioengineprototype.audioutils

import kotlin.math.log2
import kotlin.math.roundToInt

private val NOTES = arrayOf(
    "C", "C#", "D", "D#", "E", "F",
    "F#", "G", "G#", "A", "A#", "B"
)

fun freqToNote(freq: Float): String {
    val noteNumber = (12 * log2(freq / 440f) + 69).roundToInt()
    val noteIndex = noteNumber % 12
    val octave = noteNumber / 12 - 1
    return "${NOTES[noteIndex]}$octave"
}
