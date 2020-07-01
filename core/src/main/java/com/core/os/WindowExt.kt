package com.core.os

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Create by LiJie at 2019-05-29
 * window扩展方法
 */

/**
 * 清除焦点
 */
fun Window.focusNotAle() {
    this.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    )
}

/**
 * 隐藏状态栏导航栏
 */
fun Window.hideNavigationBar() {
    this.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    uiOptions = if (Build.VERSION.SDK_INT >= 19) {
        uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_IMMERSIVE
    } else {
        uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
    }
    this.decorView.systemUiVisibility = uiOptions
    this.decorView.setOnSystemUiVisibilityChangeListener {
        this.decorView.systemUiVisibility = uiOptions
    }
}

/**
 * 允许获取焦点
 */
fun Window.clearFocusNotAle() {
    this.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
}