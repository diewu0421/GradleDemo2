package com.example.zlw

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.utils.FileUtils
import org.apache.tools.ant.TaskContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File
import java.util.*
import java.util.jar.JarFile

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

                applicationVariant.name.capitalize().takeIf { it == "Release" }?.also { name ->

                    it.tasks.findByName("compile${name}JavaWithJavac")?.let { task ->
                        task.doLast {
                            println("ffffffffffffffffffffff ${it.inputs.files.files}")
                            task.outputs.files.files.forEach { file ->
                                when {
                                    file.absolutePath.endsWith(".jar") -> {
                                        JarFile(file, false)


                                    }
                                    file.absolutePath.endsWith(".class") -> {

                                    }

                                }

                            }
                        }
                    }

                }

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
                    ?.doLast { task ->

                        task?.outputs?.files?.files?.forEach { file ->
                            if (file.name.endsWith("mapping.txt")) {
                                project.logger.error("备份mapping文件")
                                if (file.exists()) {
//                                    FileUtils.copyFile(file, File(outputDir, "mapping.txt"))
                                }
                            }
                        }
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

    private fun handleClass() {

    }

    private fun handleJar() {

    }
}