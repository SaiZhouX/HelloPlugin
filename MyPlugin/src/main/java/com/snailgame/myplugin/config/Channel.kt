package com.snailgame.myplugin.config

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 渠道子包信息的封装类。
 * <pre>
 *          oppo {
 *              // 指定接入渠道子包版本
 *              //（缺省则缓存当前最新版本，在不更改版本号或清理版本缓存的情况下，不会变更版本）
 *              version = "3.0.0_2.0.4"
 *              // 可选 指定渠道的versionCode，用于更新的标识
 *              versionCode = 1
 *              // 可选 指定渠道的versionName，用于更新的标识
 *              versionName = "1.1"
 *              // 可选 指定渠道的编译的minSdkVersion
 *               minSdk = 20
 *              // 可选 指定渠道的编译的targetSdkVersion
 *              targetSdk = 28
 *              // 可选 指定渠道的包名后缀，有些渠道需要特殊设置，填空字符串则去除后缀，填NULL或者不填则为渠道名
 *              suffix = "nearme.gamecenter"
 *              // 可选 指定渠道的签名是否禁用v2签名，默认的release apk包含v1、v2的签名，禁用v2，则只使用v1签名。
 *              // 除非渠道要求，否则不推荐禁用
 *              enableV2Signing = false
 *              // 可选 用于开启或关闭渠道的多dex模式，用于解决64K引用问题(# methods: **** > 65536)
 *              multiDexEnabled = true
 *
 *              // 可选 用于自定义签名，若不配置，则使用公司默认签名,配置方式和Android提供的一致
 *              // kotlin写法
 *              signingConfig {
 *                  it.storeFile("anysdk-config/xxx/xxx.keystore")
 *                  it.storePassword = “xxx”
 *                  it.keyAlias = "xxx"
 *                  it.keyPassword = "xxx"
 *              }
 *
 *              // groovy写法
 *              signingConfig.storeFile("anysdk-config/xxx/xxx.keystore")
 *              signingConfig.storePassword = "xxx"
 *              signingConfig.keyAlias = "xxx"
 *              signingConfig.keyPassword = "xxx"
 *
 *              // 可选 用于开启或关闭计费沙盒
 *              debug = false
 *
 *              // 可多配 添加自定义meta-data, 参数皆为String类型
 *              metadata("key", "value")
 *
 *              // 可选 指定对应的so
 *              abiFilters("arm64-v8a", "armeabi-v7a")
 *
 *              // 可选 以 "渠道名_xxx" 命名的渠道是否复用原始渠道的配置
 *              // (包括anysdk-config/config.properties、anysdk-config/assets、anysdk-config/res、anysdk-config/java)
 *              multiplexConfig = true
 *          }
 * </pre>
 */

@Serializable
open class Channel(var name: String) {
    /**
     * 渠道的后缀，因为大部分渠道都要求在游戏的包名后加上后缀名，后缀名Wepack会自动指配（通常就是渠道名称）。
     * 打包人员也可手动进行指配。
     */
    @SerialName("suffix")
    var suffix: String? = null

    /**
     * 渠道子包的版本号。因为每个渠道子包都可能会有多个版本，版本号允许打包人员指定使用哪个版本的渠道子包。
     */
    @SerialName("version")
    var version: String? = null

    /**
     * 对应build.gradle中defaultConfig的versionCode，用于给当前的渠道单独指定versionCode。
     */
    @SerialName("versionCode")
    var versionCode: Int? = null

    /**
     * 对应build.gradle中defaultConfig的versionName，用于给当前的渠道单独指定versionName。
     */
    @SerialName("versionName")
    var versionName: String? = null

    /**
     * 对应defaultConfig的minSdk，用于给点钱渠道单独指定minSdk
     */
    @SerialName("minSdk")
    var minSdk: Int? = null

    /**
     * 对应defaultConfig的targetSdk，用于给点钱渠道单独指定targetSdk
     */
    @SerialName("targetSdk")
    var targetSdk: Int? = null

    @SerialName("enableV1Signing")
    var enableV1Signing: Boolean = true

    /**
     * 用于开启或关闭渠道的v2签名，不指定的话默认开启
     */
    @SerialName("enableV2Signing")
    var enableV2Signing: Boolean = true
    @SerialName("enableV3Signing")
    var enableV3Signing: Boolean = true
    @SerialName("enableV4Signing")
    var enableV4Signing: Boolean = true

    /**
     * 用于自定义签名，若不配置，则使用公司默认签名
     */
//    @SerialName("signingConfig")
//    @Contextual
//    var signingConfig: SigningConfig = SigningConfig(name + "SigningConfig")

    /**
     * 用于开启或关闭渠道的多dex模式，用于解决64K引用问题(# methods: **** > 65536)
     */
    @SerialName("multiDexEnabled")
    var multiDexEnabled: Boolean? = null

    /**
     * 用于开启或关闭计费沙盒
     */
    @SerialName("debug")
    var debug: Boolean? = null

    /**
     * 用于设置自定义meta-data
     */
    @SerialName("metadataMap")
    var metadataMap = HashMap<String, String>()

    /**
     * 用于设置包名
     */
    @SerialName("applicationId")
    var applicationId: String? = null

    /**
     * 设置abiFilters
     */
    @SerialName("abiFilters")
    var abiFilters: MutableList<String> =  mutableListOf()

    /**
     * 是否复用本渠道已有的配置
     */
    @SerialName("multiplexConfig")
    var multiplexConfig: Boolean = false

    /**
     * 用于设置自定义meta-data, <meta-data android:name="" android:value="" >
     * @param name
     * @param value
     */
    fun metadata(name: String, value: String) {
        metadataMap[name] = value
    }

    /**
     * 设置abiFilters
     * @param abiFilters
     */
    fun abiFilters(vararg abiFilters: String) {
        abiFilters.filter {
            this.abiFilters.add(it)
        }
    }

    /**
     * 用于自定义签名，若不配置，则使用公司默认签名
     * @param signingConfig
     */
//    fun signingConfig(closure: SigningConfig.() -> Unit) {
//        signingConfig.closure()
//    }
}