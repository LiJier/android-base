package com.core.vo

import androidx.lifecycle.MutableLiveData

/**
 * Create by LiJie at 2019-05-28
 * 封装有状态的LiveData
 */
class ResLiveData<T> : MutableLiveData<Resource<T>>() {

    val status: Status?
        get() = value?.status

    val data: T?
        get() = value?.data

    val progress: Int?
        get() = value?.progress

    val message: String?
        get() = value?.message

    val error: Throwable?
        get() = value?.error

    /**
     * 发送loading状态
     */
    fun loading(progress: Int? = null, message: String? = null) {
        postValue(Resource.loading(progress, data, message))
    }

    /**
     * 成功时发送数据
     */
    fun success(data: T? = null, message: String? = null) {
        postValue(Resource.success(data, progress, message))
    }

    /**
     * 错误时发送Throwable
     */
    fun error(error: Throwable, message: String? = null) {
        postValue(Resource.error(error, data, progress, message))
    }

}