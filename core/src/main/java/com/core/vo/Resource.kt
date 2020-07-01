package com.core.vo

/**
 * Create by LiJie at 2019-05-27
 * LiveData数据封装，可以发送状态
 */
data class Resource<out T, out P>(
    val status: Status,
    val data: T?,
    val progress: P?,
    val error: Throwable?
) {

    companion object {
        /**
         * 获取成功状态的Resource
         */
        fun <T, P> success(data: T?, progress: P?): Resource<T, P> {
            return Resource(Status.SUCCESS, data, progress, null)
        }

        /**
         * 获取错误状态的Resource
         */
        fun <T, P> error(error: Throwable, data: T?, progress: P?): Resource<T, P> {
            return Resource(Status.ERROR, data, progress, error)
        }

        /**
         * 获取loading状态的Resource
         */
        fun <T, P> loading(progress: P?, data: T?): Resource<T, P> {
            return Resource(Status.LOADING, data, progress, null)
        }
    }

}

/**
 * 状态
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

/**
 * 成功时调用
 */
inline fun <T, P> Resource<T, P>.onSuccess(onSuccess: (T?, P?) -> Unit): Resource<T, P> {
    return if (this.status == Status.SUCCESS) {
        this.also { onSuccess.invoke(it.data, it.progress) }
    } else {
        this
    }
}

/**
 * 加载中时调用
 */
inline fun <T, P> Resource<T, P>.onLoading(onLoading: (T?, P?) -> Unit): Resource<T, P> {
    return if (this.status == Status.LOADING) {
        this.also { onLoading.invoke(it.data, it.progress) }
    } else {
        this
    }
}

/**
 * 错误时调用
 */
inline fun <T, P> Resource<T, P>.onError(onError: (Throwable?, T?, P?) -> Unit): Resource<T, P> {
    return if (this.status == Status.ERROR) {
        this.also { onError.invoke(this.error, it.data, it.progress) }
    } else {
        this
    }
}