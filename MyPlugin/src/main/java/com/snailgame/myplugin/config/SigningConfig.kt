package com.snailgame.myplugin.config

import com.android.builder.signing.DefaultSigningConfig
import com.android.build.api.dsl.ApkSigningConfig
import com.android.build.api.dsl.SigningConfig
import com.snailgame.myplugin.MyPlugin
import com.snailgame.myplugin.config.ConfigManager.Companion.ANYSDK_CONFIG_DIR
import java.io.File

/**
 * 渠道子包信息的封装类。
 */
class SigningConfig(name: String) : DefaultSigningConfig(name), SigningConfig, ApkSigningConfig {
    override var _storeFilePath: String? = null
    override var storeFile: File? = null
    override var enableV1Signing: Boolean? = null
    override var enableV2Signing: Boolean? = null
    override var enableV3Signing: Boolean? = null
    override var enableV4Signing: Boolean? = null
    override var keyAlias: String? = null
    override var keyPassword: String? = null
    override var storePassword: String? = null
    override var storeType: String? = null

    fun storeFile(path: String) {
        if (path.startsWith(ANYSDK_CONFIG_DIR)) {
            this.storeFile = File(MyPlugin.project.projectDir, path)
        } else {
            this.storeFile = File(path)
        }
    }

    override fun initWith(that: SigningConfig) {
         this.keyAlias = that.keyAlias
         this.keyPassword = that.keyPassword
         this.storeFile = that.storeFile
         this.storePassword = that.storePassword
    }
}