package com.core.http

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Create by LiJie at 2019-05-27
 * 网络请求，支持不同baseUrl
 */
object ApiService {

    lateinit var baseUrl: String
    lateinit var okHttpClient: OkHttpClient
    val retrofitCache = HashMap<String, Retrofit>()
    val retrofitServiceCache = HashMap<String, Any>()

    /**
     * 初始化
     * @param baseUrl 不能为空
     * @param okHttp 为空时使用默认值
     * @param retrofit 为空时使用默认值
     */
    fun init(
        baseUrl: String,
        okHttp: OkHttpClient? = null,
        retrofit: Retrofit? = null
    ): ApiService {
        retrofitCache.clear()
        retrofitServiceCache.clear()
        ApiService.baseUrl = baseUrl
        okHttp?.let {
            okHttpClient = it
        } ?: run {
            okHttpClient = defaultOkHttp()
        }
        retrofit?.let {
            retrofitCache[baseUrl] = it
        } ?: run {
            retrofitCache[baseUrl] = defaultRetrofit()
        }
        return this
    }

    /**
     * 默认okHttp
     */
    fun defaultOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    /**
     * 默认Retrofit
     */
    fun defaultRetrofit(): Retrofit {
        if (ApiService::okHttpClient.isInitialized.not()) {
            okHttpClient = defaultOkHttp()
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .callFactory(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 获取api接口，支持默认baseUrl，注解的baseUrl，和参数传入baseUrl
     * 优先级为传入baseUrl、注解baseUrl、默认baseUrl
     */
    inline fun <reified T : Any> api(customBaseUrl: String? = null): T {
        synchronized(this) {
            val tBaseUrl = customBaseUrl?.let {
                it
            } ?: run {
                val clazz = T::class.java
                val annotation = clazz.getAnnotation(BaseUrl::class.java)
                annotation?.value ?: baseUrl
            }
            val retrofit = if (retrofitCache.containsKey(tBaseUrl)) {
                retrofitCache[tBaseUrl]!!
            } else {
                retrofitCache[baseUrl]!!.newBuilder()
                    .baseUrl(tBaseUrl)
                    .build()
            }
            return if (retrofitServiceCache[T::class.java.name + tBaseUrl] == null) {
                val t = retrofit.create(T::class.java)
                retrofitServiceCache[T::class.java.name + tBaseUrl] = t
                t
            } else {
                retrofitServiceCache[T::class.java.name + tBaseUrl] as T
            }
        }
    }

}