package com.snailgame.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.api.dsl.ApplicationExtension
import com.snailgame.myplugin.config.ConfigManager
import com.snailgame.myplugin.config.WepackConfig
import org.gradle.configurationcache.extensions.capitalized

class MyPlugin : Plugin<Project> {
    companion object {
        @JvmStatic
        lateinit var project: Project

        @JvmStatic
        lateinit var android: ApplicationExtension

        @JvmStatic
        lateinit var wepackConfig: WepackConfig

        @JvmStatic
        lateinit var configManager: ConfigManager
    }

    override fun apply(target: Project) {
        println("MyPlugin is applied")

        MyPlugin.project = target
        android = target.extensions.getByType(ApplicationExtension::class.java)
        wepackConfig = target.extensions.create("wepackConfig", WepackConfig::class.java, target)
        configManager = ConfigManager()

        if (wepackConfig.channels.isEmpty()) {
            // 打包人员没有配置任何渠道，因此不用再执行接下来的Wepack逻辑
            println("未进行渠道配置")
            return
        }

        generateProductFlavors()

        addBuildChannel()

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
        for (channel in wepackConfig.channels) {
            android.productFlavors {
                create(channel.name) {

                }

                println("productFlavors：" + getByName(channel.name).name)
            }
        }
    }

    /**
     * 根据渠道名，添加编译渠道Gradle命令
     */
    private fun addBuildChannel() {
        project.gradle.projectsEvaluated {
            for (channel in wepackConfig.channels) {
                project.tasks.getByName("assemble${channel.name.capitalized()}").group =
                    "wepack-${channel.name}"
                project.tasks.getByName("assemble${channel.name.capitalized()}Release").group =
                    "wepack-${channel.name}"
                project.tasks.getByName("assemble${channel.name.capitalized()}Debug").group =
                    "wepack-${channel.name}"
                project.tasks.getByName("bundle${channel.name.capitalized()}Release").group =
                    "wepack-${channel.name}"
                project.tasks.getByName("bundle${channel.name.capitalized()}Debug").group =
                    "wepack-${channel.name}"
            }
        }
    }
}