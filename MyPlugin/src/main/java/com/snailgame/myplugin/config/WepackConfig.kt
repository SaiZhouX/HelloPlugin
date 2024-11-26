package com.snailgame.myplugin.config

import org.gradle.api.Project
import org.gradle.api.NamedDomainObjectContainer
import java.io.File
import java.io.Serializable

/**
 * Wepack DSL配置，用于在Gradle中提供Wepack专属的DSL语法。
 * 打包人员可以使用如下语法结果在global-config.gradle文件中指定要打哪些渠道包:
 *
 * <pre>
 * wepackConfig {
 *     // 可选 设置应用程序的id
 *     applicationId = "com.snailgame.jy"
 *
 *     // 可选 默认关闭
 *     // 设置全渠道<meta-data name="SNAIL_DEBUG_MODE" value="true">,用于开启或关闭计费调试沙盒
 *     debug = true
 *
 *     // 可选 指定打包名称 {productName}-{versionName}-{versionCode}-{time}.apk
 *     productName = "jy"
 *
 *     // 可选 指定release apk发布路径
 *     releasePath = new File(path)
 *
 *     // 可选 可多配
 *     // 添加assets资源目录，最终编译进APK
 *     assets(new File(neiPath))
 *     // assets(new File(waiPath))
 *     assets(new File(publicPath))
 *
 *    channels {
 *        mi()
 *        vivo()
 *        lenovo()
 *    }
 * }
 * </pre>
 */

open class WepackConfig(project: Project) : Serializable {
    var mavenUrl: String = "http://10.103.4.47:8081/"

    /**
     * 应用程序的id
     */
    var applicationId: String? = null

    /**
     * 设置全局<meta-data>,用于开启或关闭计费调试沙盒
     */
    var debug: Boolean? = null

    /**
     * 指定打包名称
     */
    var productName: String? = null

    /**
     * 发布路径
     */
    var releasePath: File? = null

    /**
     * assets编译目录集合
     */
    var assets = HashSet<File>()

    /**
     * 打包人员指定的渠道列表
     */
    var channels: NamedDomainObjectContainer<Channel> = project.container(Channel::class.java)

    /**
     * 添加assets编译目录
     * @param file
     */
    fun assets(file: File) {
        assets.add(file)
    }
}