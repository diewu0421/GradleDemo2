package com.example.patch_lib

import android.app.Application
import android.content.Context
import android.os.FileUtils
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Array.newInstance

/**
 * 浙江集商优选电子商务有限公司
 * @author zenglw
 * @date   12/15/20 11:10 PM
 */
class Hotfix {

    companion object {

        fun InputStream.copyFile(outputStream: OutputStream, maxBufferSize: Int = 8 * 1024): Long {
            var totalByteSize = 0L

            val buffer = ByteArray(maxBufferSize)
            var curBufferSize = read(buffer)

            while (curBufferSize != -1) {

                outputStream.write(buffer, 0, curBufferSize)

                totalByteSize += curBufferSize

                curBufferSize = read(buffer)
            }


            return totalByteSize
        }

        fun installPatch(app: Application, path: String) {

            // 拷贝asserts目录下的hack.dex到应用cache目录
            // 拷贝asserts目录下的hack.dex到应用cache目录
            // 拷贝asserts目录下的hack.dex到应用cache目录
            // 拷贝asserts目录下的hack.dex到应用cache目录

            File(app.getDir("hack", Context.MODE_PRIVATE), "hack.dex")
                .let { hackFile ->
//                    app.assets.open("hack.dex").copyFile(hackFile.outputStream())
                    // 拿到File添加extraList中
                    arrayListOf(hackFile, File(path))
                }
                .let { extraList ->
                    val pathListField = ShareReflectHelper.findField(app.classLoader, "pathList")
                    val pathList = pathListField.get(app.classLoader)!!
                    val method = ShareReflectHelper.findMethod(
                        pathList,
                        "makePathElements",
                        List::class.java,
                        File::class.java,
                        List::class.java
                    )

                    val dexElements = method.invoke(pathList, extraList, app.cacheDir, arrayListOf<IOException>()) as Array<*>
                    val dexElementsField  = ShareReflectHelper.findField(pathList, "dexElements")
                    val oldDex = dexElementsField.get(pathList) as Array<*>

                    val newDex = newInstance(oldDex.javaClass.componentType!!, oldDex.size + extraList.size)  as Array<*>
                    System.arraycopy(dexElements, 0, newDex, 0, dexElements.size)
                    System.arraycopy(oldDex, 0, newDex, dexElements.size, oldDex.size)

                    dexElementsField.set(pathList, newDex)
                }


        }

    }
}