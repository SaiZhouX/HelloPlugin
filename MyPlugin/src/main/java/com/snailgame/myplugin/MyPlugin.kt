package com.snailgame.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import com.snailgame.myplugin.config.ConfigManager
import com.snailgame.myplugin.config.WepackConfig
import org.gradle.configurationcache.extensions.capitalized
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyPlugin : Plugin<Project> {
    companion object {
        @JvmStatic
        lateinit var project: Project

        @JvmStatic
        lateinit var android: ApplicationExtension

        @JvmStatic
        lateinit var appAndroidComponents: ApplicationAndroidComponentsExtension

        @JvmStatic
        lateinit var wepackConfig: WepackConfig

        @JvmStatic
        lateinit var configManager: ConfigManager
    }

    override fun apply(target: Project) {
        println("MyPlugin is applied")

        MyPlugin.project = target
        android = target.extensions.getByType(ApplicationExtension::class.java)
        appAndroidComponents =
            project.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)
        wepackConfig = target.extensions.create("wepackConfig", WepackConfig::class.java, target)
        configManager = ConfigManager()

        if (wepackConfig.channels.isEmpty()) {
            // 打包人员没有配置任何渠道，因此不用再执行接下来的Wepack逻辑
            println("未进行渠道配置")
            return
        }

        generateProductFlavors()

        configOutput()

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
        val weDimension = "wepack"
        android.flavorDimensions.add(weDimension)

        for (channel in wepackConfig.channels) {
            android.productFlavors {
                create(channel.name) {

                }

                println("productFlavors：" + getByName(channel.name).name)
            }
        }
    }

    /**
     * 打包相关的配置
     */
    private fun configOutput() {
        // productName默认使用工程名，若配置productName，则使用用户配置的名称
        var productName = project.name
        if (wepackConfig.productName != null && wepackConfig.productName!!.isNotBlank()) {
            productName = wepackConfig.productName!!
        }

        // 遍历所有Variant，修改保存文件名，若配置releasePath，则拷贝一份release apk到该路径
        appAndroidComponents.onVariants { variant ->
            variant.outputs.forEach { output ->
                if (output is ApkVariantOutputImpl) {
                    output.outputFileName =
                        "${productName}_${variant.flavorName}_${output.versionName}" +
                                "_${output.versionCode}_${variant.buildType}.apk"

                    if (wepackConfig.releasePath != null && "release" == variant.buildType) {
                        output.assemble.doLast {
                            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
                            val time = dateTimeFormatter.format(LocalDateTime.now())
                            val targetFile = File(
                                wepackConfig.releasePath,
                                "${output.versionName}_${output.versionCode}" +
                                        "/${productName}_${variant.flavorName}" +
                                        "_${output.versionName}_${output.versionCode}_${time}.apk"
                            )
                            output.outputFile.copyTo(targetFile)
                        }
                    }
                }
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