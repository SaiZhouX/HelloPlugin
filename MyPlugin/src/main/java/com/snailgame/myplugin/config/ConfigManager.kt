package com.snailgame.myplugin.config

import com.snailgame.myplugin.MyPlugin
import kotlinx.serialization.json.Json
import java.io.File
import java.net.URL


class ConfigManager {
    companion object {
        /**
         * anysdk-config目录名常量。
         */
        @JvmField
        val ANYSDK_CONFIG_DIR = "anysdk-config"

        /**
         * wepack.data文件名常量。
         */
        @JvmField
        val WEPACK_FILE = "wepack.data"

        /**
         * global-config.gradle文件名常量
         */
        @JvmField
        val GLOBAL_CONFIG_FILE = "global-config.gradle"

        @JvmField
        val SIGN_FILE = "snail.keystore";

        /**
         * anysdk-config目录， 所有anysdk相关的配置文件都会放置到此目录当中。
         */
//        @JvmField
//        var anySDKConfigDir = File(MyPlugin.project.projectDir, ANYSDK_CONFIG_DIR)
    }

    /**
     * global-config.gradle文件，打包人员需要在此文件中指定要打哪些渠道，以及渠道的配置信息
     */
    private var globalConfigFile = File(MyPlugin.project.projectDir, GLOBAL_CONFIG_FILE)

    /**
     * 此文件用于记录打包人员在build.gradle文件中配置的wepackConfig信息。
     */
//    var wepackFile = File(anySDKConfigDir, WEPACK_FILE)

    init {
//        ensureGlobalConfigExists()
//        ensureAnySDKConfigDirExists()
        MyPlugin.project.apply {
            from(globalConfigFile.path)
        }
    }

    /**
     * 检查打包人员在wepackConfig中配置的渠道名是否正确，如果配置了不正确的渠道名，将会抛出一个异常并终止后续的打包逻辑。
     * 如果没有设置版本，则设置为最新版本
     * @param wepackConfig
     *          记录打包人员配置的wepackConfig中的信息
     */
//    fun ensureAllChannelsAreValid(wepackConfig: WepackConfig) {
//        val channels = wepackConfig.channels
//        for (channel in channels) {
//            val realChannelName = Utils.getChannelName(channel)
//
//            // 逐个检查wepackConfig配置的渠道和版本是否正确
//            if (channel.version != null) {
//                //def data = NetworkSource.fetchChannel(channel.name, channel.version)
//                val data = NetworkSource.fetchSnapshotChannel(realChannelName, channel.version)
//                println("$realChannelName -- ${channel.version} : $data")
//                if (data == null) {
//                    val builder = StringBuffer()
//                    builder.append(realChannelName).append(" ")
//                    builder.append(channel.version)
//                    builder.append(" 这个渠道名或版本暂时还不被支持，请检查${GLOBAL_CONFIG_FILE}中的配置。\n")
//                    builder.append("执行wepack->listChannel任务可以查看目前所支持的所有渠道名称以及其对应的版本。")
//                    throw IllegalArgumentException(builder.toString())
//                } else {
//                    if (Utils.isSnapshotVersion(channel.version)) {
//                        MyPlugin.project.logger.info("检测到${realChannelName}渠道配置了渠道包测试版本[${channel.version}],拉取最新子版本[${data.version}]")
//                        channel.version = data.version
//                    }
//                }
//            } else {
//                // 如果缓存文件中不存在渠道子包的版本号，则向maven仓库发起网络请求，找到该渠道最新的版本号，并存入缓存文件
//                val latestChannel = NetworkSource.fetchLatestChannelInfo(realChannelName)
//                channel.version = latestChannel.version
//                println("--${realChannelName} set version to latest: ${channel.version}")
//            }
//        }
//    }

    /**
     * 根据WepackConfig配置生成wepack.data文件，该文件用于记录打包人员在build.gradle文件中添加了哪些渠道，以及每个渠道的配置信息。
     * @param WepackConfig
     *          记录打包人员配置的wepackConfig中的信息
     */
//    fun generateWepackDataFile(wepackConfig: WepackConfig) {
//        var oldCachedMap = getCachedChannelsFromFile()
//        // 如果原缓存文件不存在，直接存入当前的版本数据
//        if (oldCachedMap == null) {
//            oldCachedMap = wepackConfig.channels.stream().collect(Collectors.toMap({ ch -> ch.name }, { ch -> ch }))
//        } else {
//            // 如果原缓存文件有数据，则更新原缓存
//            for(channel in wepackConfig.channels) {
//                oldCachedMap[channel.name] = channel
//            }
//        }
//        if (!wepackFile.exists()) {
//            wepackFile.createNewFile()
//        }
//        val jsonContent = Json.encodeToString(oldCachedMap)
//        val stringBuilder = StringBuilder()
//        stringBuilder.append(jsonContent)
//        stringBuilder.append("\n// This file is generated automatically. DO NOT MODIFY OR DELETE IT!")
//        wepackFile.writeText(stringBuilder.toString())
//    }

    /**
     * 根据WepackConfig中配置的渠道列表自动下载相应的渠道配置文件到anysdk-config目录下，打包人员通过配置该文件中的内容来实现合包功能。
     * @param wepackConfig
     *          记录打包人员配置的wepackConfig中的信息
     */
//    fun downloadChannelConfigFiles(wepackConfig: WepackConfig) {
//        // TODO 下载部分是否需要判断本地已存在就不下载，以及文件存放位置是否要分版本，需后续讨论确定
//        wepackConfig.channels.forEach { channel ->
//            val realChannelName = Utils.getChannelName(channel)
//
//            val versionDir = File(anySDKConfigDir, "${channel.name}/.version")
//            val channelConfigDir = File(versionDir, channel.version!!)
//
//            val toFile = File(channelConfigDir, "config.xml")
//            if (toFile.exists()) return@forEach
//            // 本地没有配置文件，从服务器下载
//            val from = NetworkSource.fetchChannelConfigTemplate(realChannelName, channel.version)
//                ?: throw WepackException("[${realChannelName}]渠道版本[${channel.version}]的配置文件未找到。")
//            //def from = "http://10.103.4.47:8081/repository/maven-releases/com/snailgame/anysdksub/${channel.name}/${channel.version}/${channel.name}-${channel.version}.template"
//
//            if (!versionDir.exists()) {
//                versionDir.mkdirs()
//            }
//            if (!channelConfigDir.exists()) {
//                channelConfigDir.mkdirs()
//            }
//            downloadFile(from, toFile)
//        }
//    }

    /**
     * 检查签名文件是否存在，不存在则下载该文件
     */
//    fun ensureSignFile() {
//        val signFile = File(anySDKConfigDir, SIGN_FILE)
//        if (!signFile.exists()) {
//            val from = MyPlugin.wepackConfig.mavenUrl + "repository/Snail-Archives/wepack/snail.keystore"
//            downloadFile(from, signFile)
//        }
//    }

//    fun ensureAllChannelsConfigAreValid(wepackConfig: WepackConfig) {
//
//        val errorConfig: MutableMap<Channel, String> = HashMap()
//
//        wepackConfig.channels.forEach {channel->
//            if (channel.multiplexConfig) {
//                return
//            }
//            var valid = true
//            val versionConfigFile = File(anySDKConfigDir, "${channel.name}/.version/${channel.version}/config.xml")
//            val configPropFile = File(anySDKConfigDir, "${channel.name}/config.properties")
//
//            println("[${channel.name}] -- check config")
//
//            val props = Properties()
//            // 用于构造注释文本
//            val comments = StringBuilder()
//            val commentMap: MutableMap<String, String> = HashMap();
//
//            val factory = DocumentBuilderFactory.newInstance()
//            val builder = factory.newDocumentBuilder()
//            val xmlDoc= builder.parse(versionConfigFile)
//            xmlDoc.documentElement.normalize()
//
//            // 正则匹配出占位符 ${APP_SECRET}、${APP_ID}
//            val regex = Regex("\\$\\{(.+)\\}")
//            val replaces: MutableList<String> = mutableListOf()
//            regex.findAll(versionConfigFile.readText()).forEach {
//                // ${APP_SECRET}、${APP_ID} => APP_SECRET、APP_ID
//                replaces.add(it.value.substring(2, it.value.length-1))
//            }
//
//            val placeholderNodeList = xmlDoc.getElementsByTagName("placeholder")
//            for (i in 0 until placeholderNodeList.length) {
//                val node = placeholderNodeList.item(i)
//                if (node.nodeType == Node.ELEMENT_NODE) {
//                    val name = node.attributes.getNamedItem("name").nodeValue
//                    val desc = node.attributes.getNamedItem("desc").nodeValue
//                    commentMap[name] = desc
//                }
//            }
//
//            // 如果配置文件存在，加载配置项，反之，初始化配置文件
//            if (!configPropFile.exists()) {
//                configPropFile.createNewFile()
//                println("[${channel.name}] -- init config file")
//            } else {
//                PropertiesUtils.load(props, configPropFile)
//            }
//            // 记录配置文件是否需要更新，如果配置文件缺失占位符，则需要更新配置文件
//            var needUpdate = false
//            // 对去重后的占位符集合进行检查
//            val replaceSet = replaces.toSet()
//            comments.append("CURRENT : [${channel.name}]-[${channel.version}] 拥有配置项 ${replaceSet.size} 个")
//            replaceSet.forEach { placeHolder->
//                comments.append("\n")
//                comments.append(placeHolder)
//                if (commentMap.containsKey(placeHolder)) {
//                    comments.append(" => ${commentMap[placeHolder]}")
//                }
//
//                // 如果未包含该占位符，则提醒用户有新的占位符有更新
//                if (!props.containsKey(placeHolder)) {
//                    MyPlugin.project.logger.error("[${channel.name}][${channel.version}] -- add new config param【${placeHolder}】！")
//                    props[placeHolder] = ""
//                    valid = false
//                    needUpdate = true
//                } else {
//                    // 如果占位符没有填写，提醒用户填写该占位符
//                    if (StringUtils.isBlank(props[placeHolder].toString())) {
//                        MyPlugin.project.logger.error("[${channel.name}] -- miss value of param [${placeHolder}]！")
//                        valid = false
//                    }
//                }
//            }
//            if (needUpdate) {
//                PropertiesUtils.store(props, configPropFile, comments.toString())
//                MyPlugin.project.logger.error("[${channel.name}] -- config file update success，please finish it！\nfile '${configPropFile.path}'")
//            }
//
//            if (!valid) {
//                errorConfig[channel] = configPropFile.path
//            } else {
//                println("[${channel.name}] -- check config success")
//            }
//        }
//
//        // 如果有渠道配置有错误，中断打包流程，提醒用户完善配置
//        if (errorConfig.isNotEmpty()) {
//            val sb = StringBuilder()
//            sb.append("Please check the following configuration files：\n")
//            sb.append("* Where:\n")
//            errorConfig.values.forEach { path ->
//                sb.append("Config file (${path}:1)\n")
//            }
//            MyPlugin.project.logger.error(sb.toString())
//            throw WepackException(sb.toString())
//        }
//    }

    /**
     * 通过wepack.data文件，还原打包人员在global-config.gradle文件中的配置的渠道信息信息。
     * @return 打包人员在global-config.gradle文件中的配置的渠道信息。
     */
//    fun getCachedChannelsFromFile(): MutableMap<String, Channel>? {
//        if (!wepackFile.exists()) {
//            // 如果channels.wepack文件不存在，此时什么都不用做
//            return null
//        }
//        val lines = wepackFile.readLines()
//        if (lines.isEmpty()) {
//            return null
//        }
//        val json = lines[0] // 获取channels.wepack文件的第一行，即为渠道列表的信息
//        val channels = Json.decodeFromString<MutableMap<String, Channel>>(json)
//        return channels
//    }

    /**
     * 判断项目工程目录下是否存在global-config.gradle文件，如果不存在，自动生成一个文件，并加入基础的模板配置。
     */
    private fun ensureGlobalConfigExists() {
        if (!globalConfigFile.exists()) {
            globalConfigFile.createNewFile()
            globalConfigFile.appendText("wepackConfig {\n")
            globalConfigFile.appendText("    channels {\n")
            globalConfigFile.appendText("        // 在这里指定要打哪些渠道，如:\n")
            globalConfigFile.appendText("        // mi {}\n")
            globalConfigFile.appendText("        // vivo {}\n")
            globalConfigFile.appendText("        // lenovo {}\n")
            globalConfigFile.appendText("        // 执行...任务可以查看目前所支持的所有渠道名称以及其对应的版本。\n")
            globalConfigFile.appendText("    }\n")
            globalConfigFile.appendText("}")
        }
    }

    /**
     * 确保anysdk-config目录一定存在，如果不存在就自动创建该目录。
     */
//    private fun ensureAnySDKConfigDirExists() {
//        if (!anySDKConfigDir.exists()) {
//            anySDKConfigDir.mkdirs()
//        }
//    }

    /**
     * 下载一个文件到本地。
     * @param from
     *          文件的URL地址
     * @param to
     *          下载到本地的完整路径名
     */
    private fun downloadFile(from: String, to: File) {
        to.outputStream().use { output->
            URL(from).openStream().use { input->
                input.transferTo(output)
            }
        }
    }
}