plugins {
    `kotlin-dsl`
    id("java-library")
    id("maven-publish")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

gradlePlugin {
    plugins {
        create("greeting-plugin") {
            id = "greeting-plugin"
            implementationClass = "com.snailgame.myplugin.MyPlugin"
        }
    }
}

//推送到本地
group = "com.snail.test"
version = "1.0.0"
publishing {
    repositories {
        maven {
            //推送到本地
            url = uri("../plugin")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation("com.android.tools.build:gradle:8.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}