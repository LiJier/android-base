package com.core.app

import android.app.Application

/**
 * Create by LiJie at 2019-05-27
 * 自定义Application的基类
 */
open class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Application
    }

}