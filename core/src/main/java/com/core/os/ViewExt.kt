package com.core.os

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Create by LiJie at 2019-05-28
 * View扩展方法
 */

//上次点击时间
var lastClickTime: Long = 0

/**
 * View点击事件，防止快速点击
 * @param duration 时间间隔
 * @param next 事件
 */
inline fun View.onClick(
    duration: Long = 500,
    crossinline next: (View) -> Unit
) {
    this.setOnClickListener {
        if ((System.currentTimeMillis() - lastClickTime) > duration) {
            next.invoke(it)
            lastClickTime = System.currentTimeMillis()
        }
    }
}

/**
 * 显示View
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/**
 * View不可见
 */
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * View不可见并不占用位置
 */
fun View.gone() {
    this.visibility = View.GONE
}

fun EditText.showInput(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun EditText.hideInput(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}
