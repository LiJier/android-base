package com.core.http

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Create by LiJie at 2019-07-24
 */
interface DownloadApi {

    @Streaming
    @GET
    suspend fun download(@Url url: String): ResponseBody

}