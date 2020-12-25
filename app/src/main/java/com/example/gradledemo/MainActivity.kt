package com.example.gradledemo

import android.Manifest
import android.app.Activity
import android.app.Person
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.content.ContextCompat
//import com.example.patch_hack.AntilazyLoad
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        AntilazyLoad()
        val cls = Class.forName("com.example.patch_hack.AntilazyLoad")

//        println("cls = $cls")
        val ob = cls.newInstance()
        cls.getDeclaredMethod("test").invoke(ob)
        Zenglw()
//        throw IndexOutOfBoundsException("exception1111111111")

        window.decorView.setBackgroundColor(Color.TRANSPARENT)
        window.setBackgroundDrawable(null)
        val threadLocal = ThreadLocal<String>()
        threadLocal.set("hhhhhhhh")
        Log.e("MainActivity","onCreate ${threadLocal.get()}")
        thread {
            Log.e("MainActivity","onCreate ${threadLocal.get()}")
        }
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

//        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),10)
        Log.e("MainActivity","onCreate ")

        findViewById<Button>(R.id.button).setOnClickListener {
//            startActivityForResult(Intent(this, SecondActivity::class.java), 100)
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
        }
        
//        Log.e("MainActivity","onCreate $classLoader")
//        Log.e("MainActivity","onCreate ${classLoader.parent}")
//        Log.e("MainActivity","onCreate ${Activity::class.java.classLoader}")


        // classloader

//        runCatching {
//            val cls = classLoader.loadClass("com.example.gradledemo.Person")
//            println("clas i$cls")
//        }.onFailure {
//            Log.e("MainActivity","onCreate onfailure ${it.message}")
//        }

//        val pathClassLoader = PathClassLoader("/sdcard/test.dex", classLoader.parent)
//        val cls = pathClassLoader.loadClass("com.example.gradledemo.Person")

//        val person  = cls.newInstance()
//        Log.e("MainActivity","onCreate $person")
//        person::class.java.getDeclaredField("name")?.let {
//            it.isAccessible = true
//            it.set(person, "我是名字")
//            log("${it.get(person)}")
//        }

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            val dexClassLoader = DexClassLoader("/sdcard/test.dex",codeCacheDir.absolutePath, null, classLoader.parent)
//            val cls = dexClassLoader.loadClass("com.example.gradledemo.Person")
//            Log.e("MainActivity","onCreate1111111 $cls")
//
//        }


//        val iv = findViewById<ImageView>(R.id.iv)
//        findViewById<SeekBar>(R.id.seekbar).setOnSeekBarChangeListener(object :
//            SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                val factor = progress * 1.0f / 100
//                iv.setColorFilter(
//                    ColorUtils.getCurrentColor(
//                        factor,
//                        ContextCompat.getColor(this@MainActivity, R.color.white),
//                        ContextCompat.getColor(this@MainActivity, R.color.black)
//                    )
//                )
//
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            }
//
//        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("MainActivity","onSaveInstanceState ")
        outState.putString("editInput", findViewById<EditText>(R.id.et).text.toString())

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("MainActivity","onRestoreInstanceState ")
        val et  = findViewById<EditText>(R.id.et)
        findViewById<EditText>(R.id.et).setText(savedInstanceState.getString("editInput", "nihao"))

        et.setSelection(et.text.toString().length)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("MainActivity","onRequestPermissionsResult ")
    }

    override fun onStop() {
        super.onStop()
        Log.e("MainActivity","onStop ")
    }

    override fun onPause() {
        super.onPause()
        Log.e("MainActivity","onPause ")
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainActivity","onResume ")
    }

    override fun onStart() {
        super.onStart()
        Log.e("MainActivity","onStart ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("MainActivity","onRestart ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity","onDestroy ")
    }

    fun log(str:String) {
        Log.e("MainActivity","log $str")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.e("MainActivity","onPostCreate ")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.e("MainActivity","onPostResume ")
    }

    override fun onContentChanged() {
        super.onContentChanged()
        Log.e("MainActivity","onContentChanged ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("MainActivity","onActivityResult ")
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("MainActivity","onNewIntent ")
    }
}
