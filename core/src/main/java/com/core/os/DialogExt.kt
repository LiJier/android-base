package com.core.os

import android.app.Dialog
import androidx.appcompat.app.AlertDialog

/**
 * Create by LiJie at 2019-05-29
 * Dialog扩展方法
 */

/**
 * 全屏显示AlertDialog，隐藏状态栏和导航栏
 */
fun AlertDialog.Builder.fullShow() {
    val alertDialog = this.create()
    alertDialog.fullShow()
}

/**
 * 全屏显示Dialog，隐藏状态栏和导航栏
 */
fun Dialog.fullShow() {
    val window = this.window
    window?.focusNotAle()
    this.show()
    window?.hideNavigationBar()
    window?.clearFocusNotAle()
}