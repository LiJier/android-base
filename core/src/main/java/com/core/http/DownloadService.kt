package com.core.http

import com.core.math.div
import com.core.math.mul
import com.core.vo.ResLiveData
import kotlinx.coroutines.*
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileOutputStream
import java.net.CookieManager
import java.util.concurrent.TimeUnit

/**
 * Create by LiJie at 2019-07-25
 */
object DownloadService {

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.MINUTES)
        .writeTimeout(20, TimeUnit.MINUTES)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiService.baseUrl)
        .callFactory(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val downloadApi = retrofit.create(DownloadApi::class.java)
    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun download(url: String, path: String, downloadProcessData: ResLiveData<Any>) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            downloadProcessData.error(throwable)
        }
        mainScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                downloadProcessData.loading(-1)
                val responseBody = downloadApi.download(url)
                val contentLength = responseBody.contentLength()
                responseBody.byteStream().use { ipt ->
                    val output = FileOutputStream(path)
                    val buffer = ByteArray(2048)
                    output.use { opt ->
                        var read: Int
                        var readLength = 0L
                        while (ipt.read(buffer).also { read = it } != -1) {
                            opt.write(buffer, 0, read)
                            readLength += read
                            downloadProcessData.loading(((readLength div contentLength) mul 100).toInt())
                        }
                    }
                }
                downloadProcessData.success()
            }
        }
    }

}