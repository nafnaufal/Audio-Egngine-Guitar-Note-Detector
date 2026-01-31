package com.example.audioengineprototype

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val AUDIO_PERMISSION_REQUEST = 1001

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (hasAudioPermission()) {
            startAudioEngine()
        } else {
            requestAudioPermission()
        }
    }

    override fun onPause() {
        super.onPause()
        stopAudioInput()
    }

    private fun hasAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestAudioPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            AUDIO_PERMISSION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AUDIO_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                startAudioEngine()
            } else {
                Log.e("PERMISSION", "Mic permission denied")
            }
        }
    }

    private fun startAudioEngine() {
        startAudioInput()
    }
}
