package com.core.os

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.core.app.CoreApp


/**
 * Create by LiJie at 2020/4/29
 */
object NetworkUtils {

    private fun getActiveNetworkInfo(): NetworkInfo? {
        val cm =
            CoreApp.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    fun isConnected(): Boolean {
        val info = getActiveNetworkInfo();
        return info != null && info.isConnected;
    }

}