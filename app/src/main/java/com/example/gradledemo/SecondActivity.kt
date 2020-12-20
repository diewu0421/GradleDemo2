package com.example.gradledemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        window.decorView.setBackgroundColor(Color.TRANSPARENT)
        Log.e("SecondActivity","onCreate ")

    }

    override fun onStop() {
        super.onStop()
        Log.e("SecondActivity","onStop ")
    }

    override fun onPause() {
        super.onPause()
        Log.e("SecondActivity","onPause ")
    }

    override fun onResume() {
        super.onResume()
        Log.e("SecondActivity","onResume ")

//        setResult(RESULT_OK)
//        finish()
    }

    override fun onStart() {
        super.onStart()

        Log.e("SecondActivity","onStart ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("SecondActivity","onRestart ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SecondActivity","onDestroy ")
    }

    fun log(str:String) {
        Log.e("SecondActivity","log $str")
    }

}