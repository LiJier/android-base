package com.core.base

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * Create by LiJie at 2019-05-28
 * Activity 管理类
 */
object ActivityManager {

    private val activitys = mutableListOf<WeakReference<Activity>>()

    /**
     * 添加activity
     */
    fun add(activity: Activity) {
        val weakReference = WeakReference(activity)
        activitys.add(weakReference)
    }

    /**
     * 移除activity
     */
    fun pop(activity: Activity) {
        var weakReference: WeakReference<Activity>? = null
        activitys.forEach {
            val get = it.get()
            if (get === activity) {
                weakReference = it
            }
        }
        weakReference?.let { activitys.remove(it) }
    }

    /**
     * 获取当前activity
     */
    fun getCurrent(): Activity? {
        return activitys.last().get()
    }

    /**
     * 清除并关闭所有activity
     */
    fun clear() {
        activitys.forEach { weakReference ->
            val get = weakReference.get()
            get?.let {
                if (it.isFinishing.not()) {
                    it.finish()
                }
            }
        }
        activitys.clear()
    }

}