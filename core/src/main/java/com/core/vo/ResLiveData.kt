package com.core.vo

import androidx.lifecycle.MutableLiveData

/**
 * Create by LiJie at 2019-05-28
 * 封装有状态的LiveData
 */
class ResLiveData<T, P> : MutableLiveData<Resource<T, P>>() {

    val status: Status?
        get() = value?.status

    val data: T?
        get() = value?.data

    val progress: P?
        get() = value?.progress

    val error: Throwable?
        get() = value?.error

    /**
     * 发送loading状态
     */
    fun loading(progress: P? = null, data: T? = null) {
        val t = data ?: value?.data
        postValue(Resource.loading(progress, t))
    }

    /**
     * 成功时发送数据
     */
    fun success(data: T? = null, progress: P? = null) {
        postValue(Resource.success(data, progress))
    }

    /**
     * 错误时发送Throwable
     */
    fun error(error: Throwable, data: T? = null, progress: P? = null) {
        val t = data ?: value?.data
        postValue(Resource.error(error, t, progress))
    }

}