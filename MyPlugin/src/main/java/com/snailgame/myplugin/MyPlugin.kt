package com.snailgame.myplugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        target.tasks.register("greeting") {
            doLast {
                println("Hello from the Greeting Plugin!")
            }
        }
    }
}