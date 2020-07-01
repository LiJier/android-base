package com.core.http

import okhttp3.internal.platform.Platform

/**
 * Create by LiJie at 2019-06-05
 */
internal interface Logger {

    fun log(message: String)

    companion object {

        val DEFAULT: Logger = object : Logger {
            override fun log(message: String) {
                Platform.get().log(4, message, null)
            }
        }
    }

}
