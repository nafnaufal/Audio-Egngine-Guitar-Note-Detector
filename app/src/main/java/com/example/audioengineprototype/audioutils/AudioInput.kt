package com.example.audioengineprototype.audioutils

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlin.concurrent.thread

private const val SAMPLE_RATE = 44100
private const val TAG = "AUDIO_INPUT"

private var audioRecord: AudioRecord? = null
private var audioThread: Thread? = null

private val stabilizer = FrequencyStabilizer(5)
private val debouncer = NoteDebouncer(80)

fun startAudioInput() {
    try {
        if (audioRecord != null) return

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_FLOAT,
            AudioRecord.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_FLOAT
            )
        )

        val buffer = FloatArray(2048)

        audioRecord?.startRecording()

        audioThread = thread(start = true) {
            while (audioRecord != null) {

                val read = audioRecord!!.read(
                    buffer, 0, buffer.size, AudioRecord.READ_BLOCKING
                )

                if (read <= 0) continue

                val freq = detectPitchYin(
                    buffer.copyOf(read),
                    SAMPLE_RATE
                )

                if (freq in 60f..1500f) {
                    val stable = stabilizer.push(freq)
                    if (stable != null) {
                        val note =
                            freqToNote(stable)
                        val confirmed = debouncer.push(note)
                        if (confirmed != null) {
                            Log.d(TAG, "NOTE: $confirmed")
                        }
                    }
                }
            }
        }

    } catch (e: SecurityException) {
        Log.e(TAG, "RECORD_AUDIO permission missing", e)
    }
}

fun stopAudioInput() {
    audioRecord?.stop()
    audioRecord?.release()
    audioRecord = null
    audioThread = null
}

