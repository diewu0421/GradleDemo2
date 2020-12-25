package com.example.zlw

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.utils.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.objectweb.asm.*
import java.io.File
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * 浙江集商优选电子商务有限公司
 *
 * @author zenglw
 * @date 12/14/20 12:34 AM
 */
class HotFixPlugin : Plugin<Project> {
    private lateinit var mProject: Project
    private var mHackJarFile: File? = null
    private val tempDir by lazy {
       "${mProject.buildDir}/tempDir"
    }

    override fun apply(project: Project) {
        this.mProject = project
        val patch = project.extensions.create("patch", PatchExtensions::class.java)
        val parentdir = File("${mProject.buildDir}/tmp/kotlin-classes/debug/com/example/gradledemo")
        if (!parentdir.exists()) {
            parentdir.mkdirs()
        }
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
                        ?.let { name ->
                            val patchHackPath = ":patch-hack:compileDebugKotlin"

                            val patchtask = project.tasks.findByPath(patchHackPath)?.also { task ->
                                task.doLast {

                                    task.outputs.files.files.forEach {
                                        generateHackJar(it)
                                    }
                                }
                                task.doFirst {
                                }
                            }

                            project.tasks.findByName("compile${name}Kotlin")?.let {task ->
                                // 必须要让patch_task任务优先执行
                                task.setDependsOn(mutableListOf(patchtask))
                                task.doLast {
                                    mHackJarFile?.copyTo(File("${mProject.buildDir}/tmp/kotlin-classes/${applicationVariant.name}/com/example/gradledemo", mHackJarFile?.name ?: "AntiLazyLoad.class"))
//                                    mHackJarFile?.delete()
                                    println("compileDebugKotlin 拷贝文件成功")
                                }
                            }

                            val minifyTask = project.tasks.findByName("minify${name}WithR8")
                            minifyTask?.doFirst { task ->
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
                        }
                }
            }
        }

    }

    private fun generateHackJar(file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach {
                generateHackJar(it)
            }
        } else if (file.name.contains("AntilazyLoad")) {

            val homeDir = System.getProperty("user.home")

            val outputDir = "${System.getProperty("user.home")}/Desktop/hack.dex"
            val dxPath = "${System.getenv("ANDROID_HOME")}/build-tools/29.0.2/dx"

            val jarOutDir = "${System.getProperty("user.home")}/Desktop/hack.jar"

            JarOutputStream(jarOutDir.toFile().outputStream()).run {

                putNextEntry(
                    JarEntry(file.path.substringAfterLast("debug").substring(1))
                )
                write(file.readBytes())
                closeEntry()
                close()
            }
            Runtime.getRuntime().exec("""$dxPath --dex --output=$outputDir $jarOutDir""").run {
                waitFor()
                if (exitValue() != 0) {
                    mProject.logger.error("打dex包失败")
                }
            }

            File(tempDir).apply {
                takeIf { !it.exists() }?.mkdirs()
                mHackJarFile = File(this, file.name)
                file.copyTo(mHackJarFile!!)
                file.delete()
            }
            println("success 打包成功")
        }
    }

    private fun String.toFile() = File(this)

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
                            super.visitLdcInsn(Type.getType("Lcom/example/patch_hack/AntilazyLoad;"))
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

    }

    private fun processJar(file: File) {

    }

}