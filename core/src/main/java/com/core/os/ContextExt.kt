package com.core.os

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import androidx.fragment.app.Fragment

/**
 * Create by LiJie at 2019-05-29
 * Context相关扩展方法
 */

/**
 * Context启动一个activity
 */
inline fun <reified T : Activity> Context.start(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java).apply {
        bundle?.let {
            putExtras(it)
        }
    }
    this.startActivity(intent)
}

/**
 * Context启动一个Activity For Result
 */
inline fun <reified T : Activity> Activity.start(requestCode: Int, bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java).apply {
        bundle?.let {
            putExtras(it)
        }
    }
    this.startActivityForResult(intent, requestCode)
}

/**
 * Fragment启动一个Activity
 */
inline fun <reified T : Activity> Fragment.start(bundle: Bundle? = null) {
    val intent = Intent(this.context, T::class.java).apply {
        bundle?.let {
            putExtras(it)
        }
    }
    this.startActivity(intent)
}

/**
 * Fragment启动一个Activity For Result
 */
inline fun <reified T : Activity> Fragment.start(requestCode: Int, bundle: Bundle? = null) {
    val intent = Intent(this.context, T::class.java).apply {
        bundle?.let {
            putExtras(it)
        }
    }
    this.startActivityForResult(intent, requestCode)
}

val Context.displayMetrics: DisplayMetrics
    get() {
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm
    }

val Context.statusBarHeight: Int
    get() {
        val resources: Resources = this.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

val Context.actionBarHeight: Int
    get() {
        val typedValue = TypedValue()
        val actionBarHeight =
            TypedValue.complexToDimensionPixelSize(
                typedValue.data,
                this.resources.displayMetrics
            );
        return actionBarHeight
    }

