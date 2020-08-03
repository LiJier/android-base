package com.core.vo

/**
 * Create by LiJie at 2019-05-27
 * LiveData数据封装，可以发送状态
 */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val progress: Int?,
    val message: String?,
    val error: Throwable?
) {

    companion object {
        /**
         * 获取成功状态的Resource
         */
        fun <T> success(data: T?, progress: Int?, message: String?): Resource<T> {
            return Resource(Status.SUCCESS, data, progress, message, null)
        }

        /**
         * 获取错误状态的Resource
         */
        fun <T> error(
            error: Throwable,
            data: T?,
            progress: Int?,
            message: String?
        ): Resource<T> {
            return Resource(Status.ERROR, data, progress, message, error)
        }

        /**
         * 获取loading状态的Resource
         */
        fun <T> loading(progress: Int?, data: T?, message: String?): Resource<T> {
            return Resource(Status.LOADING, data, progress, message, null)
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
inline fun <T> Resource<T>.onSuccess(onSuccess: (T?) -> Unit): Resource<T> {
    return if (this.status == Status.SUCCESS) {
        this.also { onSuccess.invoke(it.data) }
    } else {
        this
    }
}

/**
 * 加载中时调用
 */
inline fun <T> Resource<T>.onLoading(onLoading: (Int?) -> Unit): Resource<T> {
    return if (this.status == Status.LOADING) {
        this.also { onLoading.invoke(it.progress) }
    } else {
        this
    }
}

/**
 * 错误时调用
 */
inline fun <T> Resource<T>.onError(onError: (Throwable?) -> Unit): Resource<T> {
    return if (this.status == Status.ERROR) {
        this.also { onError.invoke(this.error) }
    } else {
        this
    }
}