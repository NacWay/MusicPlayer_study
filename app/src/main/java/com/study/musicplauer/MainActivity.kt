package com.study.musicplauer

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null
    var curentTracks: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        askNotificationPermission()

        val play: ImageButton = findViewById(R.id.play)
        val pause: ImageButton = findViewById(R.id.pause)
        val next: ImageButton = findViewById(R.id.next)
        val previous: ImageButton = findViewById(R.id.previous)
        val namedTrak: TextView = findViewById(R.id.named)

        namedTrak.text = getString(getMusic()[curentTracks]).substringAfterLast('/')

        play.setOnClickListener {
            if (mMediaPlayer == null) {
                MediaPlayerService.startService(this, "Service is start", curentTracks)
                mMediaPlayer = MediaPlayer.create(this, getMusic()[curentTracks])
                mMediaPlayer!!.isLooping = false
                namedTrak.text = getString(getMusic()[curentTracks]).substringAfterLast('/')
                mMediaPlayer!!.start()
            } else mMediaPlayer!!.start()
        }

        pause.setOnClickListener {
            if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
        }

        next.setOnClickListener {
            mMediaPlayer!!.release()
            curentTracks++
            if (curentTracks < getMusic().size) {
                mMediaPlayer = MediaPlayer.create(this, getMusic()[curentTracks])
                namedTrak.text = getString(getMusic()[curentTracks]).substringAfterLast('/')
            } else {
                curentTracks = 0
                mMediaPlayer = MediaPlayer.create(this, getMusic()[0])
                namedTrak.text = getString(getMusic()[curentTracks]).substringAfterLast('/')
            }
            mMediaPlayer!!.start()

        }


        previous.setOnClickListener {
            mMediaPlayer!!.release()
            if (curentTracks != 0) {
                curentTracks--
                mMediaPlayer = MediaPlayer.create(this, getMusic()[curentTracks])
                namedTrak.text = getString(getMusic()[curentTracks]).substringAfterLast('/')
            }
            if (curentTracks == 0) {
                mMediaPlayer = MediaPlayer.create(this, getMusic()[0])
                namedTrak.text = getString(getMusic()[curentTracks]).substringAfterLast('/')
                curentTracks = 0
            }
            mMediaPlayer!!.start()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer!!.release()
        MediaPlayerService.stopService(this)
    }

    // Запрос разрешения на показ уведомлений
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            //  Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show()
        } else {
            //  Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show()
        }
    }

    // Запрос разрешения на показ уведомлений
    private fun askNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            )
            else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
