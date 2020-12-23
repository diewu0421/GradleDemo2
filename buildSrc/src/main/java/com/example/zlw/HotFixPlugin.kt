package com.example.zlw

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.utils.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.*
import java.io.File
import java.util.*

/**
 * 浙江集商优选电子商务有限公司
 *
 * @author zenglw
 * @date 12/14/20 12:34 AM
 */
class HotFixPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val patch = project.extensions.create("patch", PatchExtensions::class.java)

        project.afterEvaluate { it ->
            println("project  =================  ${patch.name}")
            val appExtension = it.extensions.findByType(AppExtension::class.java) as AppExtension

            appExtension.applicationVariants.all { applicationVariant: ApplicationVariant ->

                applicationVariant.name.capitalize().takeIf { it == "Debug" }?.also { name ->


                    var outputDir: String? = null
                    applicationVariant.name
                        .capitalize()
                        .also { name ->
                            outputDir =
                                patch.output ?: "${project.buildDir}/patch/${name.toLowerCase()}"
                            println("文件目录是否cunzai ${File(outputDir).exists()}")
                            File(outputDir).takeIf { !it.exists() }?.mkdirs()

                        }
                        .takeIf { it == "Debug" }
                        ?.let { name -> project.tasks.findByName("minify${name}WithR8") }
                        ?.doFirst { task ->

                            task.outputs.files.files.forEach { file ->
                                if (file.name.endsWith("mapping.txt")) {
                                    project.logger.error("备份mapping文件")
                                    if (file.exists()) {
                                        FileUtils.copyFile(file, File(outputDir, "mapping.txt"))
                                    }
                                }
                            }
                            task.inputs.files.files.forEach {
                                handleFile(it, applicationVariant)
                            }
                        }
//                    it.tasks.findByName("compile${name}JavaWithJavac")?.let { task ->
//                        task.doLast {
//                            it.inputs.files.files.forEach {
//                                println(it.absolutePath)
//                            }
////                            println("inputs ${it.inputs.files.files}")
////                            println("outputs ${it.outputs.files.files}")
//                            task.outputs.files.files.forEach { file ->
////                                file.readText()
//                                when {
//                                    file.absolutePath.endsWith(".jar") -> {
//
//
//                                    }
//                                    file.absolutePath.endsWith(".class") -> {
//
//                                    }
//
//                                }
//
//                            }
//                        }
//                    }
//                    println("find task ${it.tasks.findByName("compile${name}Kotlin")}")
//                    it.tasks.findByName("compile${name}Kotlin")?.let {task ->
//                        task.doLast {
//                            it.inputs.files.files.forEach {
//                                println(it.absolutePath)
//                            }
////                            println("kotlin compile ${task.inputs.files.files}")
////                            println("kotlin compile ${task.outputs.files.files}")
//
//                        }
//                    }
//                    it.tasks.findByName("assemble${name}")?.let {task ->
//                        task.doLast {
//                            println("asdfaffffffffffffffffffffffff")
//                            it.inputs.files.files.forEach {
//                                println(it.absolutePath)
//                            }
////                            println("kotlin compile ${task.inputs.files.files}")
////                            println("kotlin compile ${task.outputs.files.files}")
//
//                        }
//                    }
                }

//                    println("gradle version ==============+${project.gradle.gradleVersion}")
//                    it.tasks.findByName("minify${name}WithR8")?.let {task ->
//                        task.doLast {
//                           it.outputs.files.files.forEach {file ->
//                               println("file ========== ${file.absolutePath}")
//                           }
//                        }
//                    }
            }
        }


    }

    private fun handleFile(file: File, applicationVariant: ApplicationVariant) {
        when {
            file.isDirectory -> {
                file.listFiles().forEach {
                    handleFile(it, applicationVariant)
                }
            }
            file.path.endsWith(".jar") -> {
                processJar(file)
            }
            file.path.endsWith(".class") -> {
                processClass(file)
            }
        }
    }

    private fun processClass(file: File) {
        if (file.path.contains("Zenglwasdfafasdf")) {

            val cr = ClassReader(file.inputStream())
            val cw = ClassWriter(cr, 0)

            val cVisitor = object : ClassVisitor(Opcodes.ASM5, cw) {

                override fun visitMethod(
                    access: Int,
                    name: String?,
                    descriptor: String?,
                    signature: String?,
                    exceptions: Array<out String>?
                ): MethodVisitor {
                    var methodVisitor = super.visitMethod(
                        access,
                        name,
                        descriptor,
                        signature,
                        exceptions
                    )
                    methodVisitor = object : MethodVisitor(api, methodVisitor) {
                        override fun visitInsn(opcode: Int) {
                            if ("<init>" == name && opcode == Opcodes.RETURN) {
                                super.visitLdcInsn(Type.getType("Lcom/enjoy/patch/hack/AntilazyLoad;"))
                            }
                            super.visitInsn(opcode)

                        }
                    }
                    return methodVisitor
                }
            }
            cr.accept(cVisitor, 0)
            val newByte = cw.toByteArray()
            file.outputStream().write(newByte)

        } else {

        }
//        file.outputStream().write(byteArrayOf(0, 1))

//        if (file.name.contains("MainActivity")) {
//           file.delete()
//        }
    }

    private fun processJar(file: File) {

    }

    private fun handleClass() {

    }

    private fun handleJar() {

    }
}