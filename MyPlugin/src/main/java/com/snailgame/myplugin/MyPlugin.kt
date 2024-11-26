package com.snailgame.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.api.dsl.ApplicationExtension

class MyPlugin : Plugin<Project> {
    companion object {
        @JvmStatic
        lateinit var android: ApplicationExtension
    }

    val channelList = listOf("apple", "banana", "orange")

    override fun apply(target: Project) {
        println("MyPlugin is applied")

        android = target.extensions.getByType(ApplicationExtension::class.java)

        generateProductFlavors()

//        target.tasks.register("greeting") {
//            doLast {
//                println("Hello from the Greeting Plugin!")
//            }
//        }
    }

    /**
     * 根据打包人员配置的渠道信息，生成productFlavors配置。productFlavors是Android Studio用于实现多渠道打包的一种方式，其配置语法如下：
     *
     * android.flavorDimensions "wepack"
     * android.productFlavors {*     mi {*          dimension "wepack"
     *          applicationId "com.snailgame.jiuyin.mi"
     *}*     vivo {*         dimension "wepack"
     *         applicationId "com.snailgame.jiuyin.vivo"
     *}*}*
     * 而Wepack则是使用脚本来自动生成上述productFlavors配置，从而实现多渠道打包功能。
     */
    private fun generateProductFlavors() {
        for (channel in channelList) {
            android.productFlavors {
                create(channel) {

                }

                println("productFlavors：" + getByName(channel).name)
            }
        }
    }
}