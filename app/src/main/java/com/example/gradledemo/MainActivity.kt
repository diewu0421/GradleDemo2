package com.example.gradledemo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("MainActivity","onCreate $classLoader")
        Log.e("MainActivity","onCreate ${classLoader.parent}")
        Log.e("MainActivity","onCreate ${Activity::class.java.classLoader}")

        val iv = findViewById<ImageView>(R.id.iv)
        findViewById<SeekBar>(R.id.seekbar).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val factor = progress * 1.0f / 100
                iv.setColorFilter(
                    ColorUtils.getCurrentColor(
                        factor,
                        ContextCompat.getColor(this@MainActivity, R.color.white),
                        ContextCompat.getColor(this@MainActivity, R.color.black)
                    )
                )

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}