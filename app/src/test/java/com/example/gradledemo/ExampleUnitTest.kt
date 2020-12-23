package com.example.gradledemo

import org.junit.Test

import org.junit.Assert.*
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.jar.Attributes
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private fun String.outputStream(): FileOutputStream {
        return FileOutputStream(this)
    }
    @Test
    fun addition_isCorrect() {
        val path = "/Users/apple/IntelliJIDEAProjects/GradleDemo/app/build/tmp/kotlin-classes/debug/com/example/gradledemo"
        val source = File(path)

        // println("path $path")
        val jarOutputStream = JarOutputStream("output.jar".outputStream(), Manifest().apply {
            mainAttributes[Attributes.Name.MANIFEST_VERSION] = "1.0"
        })
        add(File(path), jarOutputStream)
    }


    private fun add(source:File, jarOutputStream: JarOutputStream) {
        if (source.isDirectory) {
            var name = source.path.replace("\\", "/")
            if (name.isNotEmpty()) {
                if (!name.endsWith("/")) {
                    name += "/"
                }
                val entry = JarEntry(name).apply {
                    time = source.lastModified()
                }
                jarOutputStream.putNextEntry(entry)
                jarOutputStream.closeEntry()

                source.listFiles()?.forEach {
                    add(it, jarOutputStream)
                }
                return
            }
        }

        jarOutputStream.putNextEntry(JarEntry(source.path.replace("\\", "/")).apply {
            time = source.lastModified()
        })

        // 把数据写进去
        println("source = $source")
        jarOutputStream.write(source.inputStream().readBytes())
        jarOutputStream.closeEntry()

    }


}