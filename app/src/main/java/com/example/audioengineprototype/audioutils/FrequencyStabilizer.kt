package com.example.audioengineprototype.audioutils

class FrequencyStabilizer(
    private val size: Int = 5
) {
    private val buffer = ArrayList<Float>(size)

    fun push(freq: Float): Float? {
        buffer.add(freq)
        if (buffer.size < size) return null
        if (buffer.size > size) buffer.removeAt(0)

        val sorted = buffer.sorted()
        return sorted[size / 2]
    }
}