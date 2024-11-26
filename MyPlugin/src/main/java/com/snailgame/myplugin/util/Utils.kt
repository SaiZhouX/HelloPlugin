package com.snailgame.myplugin.util

import com.snailgame.myplugin.config.Channel

class Utils {
    companion object {
        /**
         * 获取channelName
         * @param channel
         * @return channelName
         */
        @JvmStatic
        fun getChannelName(channel: Channel): String {
            if (channel.name.indexOf("_") != -1) {
                val channelName = channel.name.split("_")
                return channelName[0]
            } else {
                return channel.name
            }
        }
    }
}