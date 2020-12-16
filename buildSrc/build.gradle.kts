//apply plugin: 'java-gradle-plugin'
//apply plugin: 'kotlin'
//
//
//buildscript {
//    ext.kotlin_version = "1.3.72"
//
//    repositories {
//        mavenCentral()
//    }
//    dependencies {
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
//
//    }
//}
//repositories {
//    mavenCentral()
//}
//
//sourceCompatibility = "1.7"
//targetCompatibility = "1.7"
println("333333333333")
//plugins {
//    id("java-gradle-plugin")
//    id("kotlin")
//}

apply {
    plugin("java-gradle-plugin")
    plugin("kotlin")
}

buildscript {
//    extensions.add("kotlin_version", "1.3.72")



    repositories {

        google()
        jcenter()
        mavenCentral()
    }

    dependencies {

        extra.apply {
            set("kotlin_version", "1.3.72")
        }

//        ext {
//            set("kotlin_version", "1.3.72")
//        }
////        println("kotlin-version  ${extensions.getByName("kotlin_version")}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${extra.get("kotlin_version")}")
    }
}

dependencies {
    println("gradle Api =================${gradleApi()}")
    implementation( "com.android.tools.build:gradle:4.0.0")

}

repositories {
    mavenCentral()
    google()
    jcenter()
}
