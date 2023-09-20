package com.study.musicplauer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null
    var curentTracks: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listTracks = listOf(
            R.raw.`fun`,
            R.raw.amazing_day,
            R.raw.army_of_one,
            R.raw.kaleidoscope
        )

        val play: ImageButton = findViewById(R.id.play)
        val pause: ImageButton = findViewById(R.id.pause)
        val next: ImageButton = findViewById(R.id.next)
        val previous: ImageButton = findViewById(R.id.previous)
        val namedTrak: TextView = findViewById(R.id.named)

        namedTrak.text= getString(listTracks[curentTracks]).substringAfterLast('/')

        play.setOnClickListener{
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(this, listTracks[curentTracks])
                mMediaPlayer!!.isLooping = false
                namedTrak.text= getString(listTracks[curentTracks]).substringAfterLast('/')
                mMediaPlayer!!.start()
            } else mMediaPlayer!!.start()
        }

        pause.setOnClickListener{
            if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
        }

        next.setOnClickListener{
            mMediaPlayer!!.release()
            curentTracks++
            if(curentTracks < listTracks.size) {
                mMediaPlayer = MediaPlayer.create(this, listTracks[curentTracks])
                namedTrak.text= getString(listTracks[curentTracks]).substringAfterLast('/')
            }
            else
            {
                curentTracks=0
                mMediaPlayer = MediaPlayer.create(this, listTracks[0])
                namedTrak.text= getString(listTracks[curentTracks]).substringAfterLast('/')
            }
            mMediaPlayer!!.start()

        }


        previous.setOnClickListener{
            mMediaPlayer!!.release()
            if(curentTracks!=0){
                curentTracks--
                mMediaPlayer = MediaPlayer.create(this, listTracks[curentTracks])
                namedTrak.text= getString(listTracks[curentTracks]).substringAfterLast('/')
            }
            if(curentTracks==0){
                mMediaPlayer = MediaPlayer.create(this, listTracks[0])
                namedTrak.text= getString(listTracks[curentTracks]).substringAfterLast('/')
                curentTracks=0
            }
            mMediaPlayer!!.start()
        }
    }

        override fun onStop() {
            super.onStop()
        }
}
