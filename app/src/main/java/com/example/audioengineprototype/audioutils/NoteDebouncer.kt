package com.example.audioengineprototype.audioutils

class NoteDebouncer(
    private val debounceMs: Long = 80
) {
    private var lastNote: String? = null
    private var lastTime: Long = 0

    fun push(note: String): String? {
        val now = System.currentTimeMillis()

        return if (note == lastNote) {
            if (now - lastTime >= debounceMs) note else null
        } else {
            lastNote = note
            lastTime = now
            null
        }
    }
}
