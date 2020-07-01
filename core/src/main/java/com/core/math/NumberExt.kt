package com.core.math

import android.content.res.Resources.NotFoundException
import android.text.format.DateFormat
import android.util.TypedValue
import androidx.core.content.res.ResourcesCompat
import com.core.base.CoreApp

/**
 * Create by LiJie at 2019-05-29
 * 数字相关扩展方法
 */

/**
 * 颜色resId转化成color
 */
val Int.color: Int
    get() = ResourcesCompat.getColor(CoreApp.appContext.resources, this, null)

/**
 * 获取指定大小的dp值
 */
val Number.dp: Int
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            CoreApp.appContext.resources.displayMetrics
        ).toInt()

/**
 * 获取指定大小的sp值
 */
val Number.sp: Int
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            CoreApp.appContext.resources.displayMetrics
        ).toInt()

/**
 * 格式化时间
 * @param format 时间格式
 */
fun Long.toTimeString(format: String = "yyyy-MM-dd HH:mm:ss"): String =
    DateFormat.format(format, this).toString()

fun Int.getDisplayName(): String {
    return if (this <= 0x00FFFFFF) {
        this.toString()
    } else try {
        CoreApp.appContext.resources.getResourceName(this)
    } catch (e: NotFoundException) {
        this.toString()
    }
}