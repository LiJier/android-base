package com.core.http

/**
 * Create by LiJie at 2019-05-27
 * 网络请求返回实体抽象接口
 */
interface IHttpResult<out T> {

    /**
     * 判断请求是否成功
     */
    fun isSuccess(): Boolean

    /**
     * 获取返回的数据
     */
    fun getResultData(): T?

    /**
     * 获取请求返回信息
     */
    fun getMessage(): String?

}