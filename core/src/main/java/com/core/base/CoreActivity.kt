package com.core.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.core.R
import com.core.os.fullShow
import com.core.os.hideNavigationBar
import com.core.os.toast
import com.core.vo.Resource
import com.core.vo.onError
import com.core.vo.onLoading
import com.core.vo.onSuccess
import kotlinx.android.synthetic.main.layout_process.*

/**
 * Create by LiJie at 2019-05-29
 * BaseActivity，封装一些常用方法
 */
open class CoreActivity : AppCompatActivity() {

    //加载进度条
    private val processDialog by lazy {
        createProcessDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.add(this)
        if (isFullScreen()) {
            window.hideNavigationBar()
        }
    }

    /**
     * 显示进度条
     */
    protected open fun showProcessDialog(processText: String? = "加载中...") {
        processDialog.process_text_view?.text = processText
        if (isFullScreen()) {
            processDialog.fullShow()
        } else {
            processDialog.show()
        }
    }

    /**
     * 隐藏进度条
     */
    protected open fun hideProcessDialog() {
        processDialog.dismiss()
    }

    /**
     * 创建进度条
     */
    @SuppressLint("InflateParams")
    protected open fun createProcessDialog(): Dialog {
        return Dialog(this, R.style.Dialog).apply {
            setContentView(
                LayoutInflater.from(this@CoreActivity)
                    .inflate(R.layout.layout_process, null, false)
            )
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    /**
     * 是否是全屏
     */
    protected open fun isFullScreen() = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (isFullScreen()) {
            window.hideNavigationBar()
        }
    }

    /**
     * 返回按钮点击事件，并将事件传递到fragment
     */
    override fun onBackPressed() {
        var back = true
        supportFragmentManager.fragments.forEach { fragment ->
            (fragment as? CoreFragment)?.onBackPressed()?.let {
                if (it) {
                    back = false
                    return@forEach
                }
            }
        }
        if (back) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.pop(this)
    }

    open fun <T, P> Resource<T, P>.onDefaultSuccess(onSuccess: ((T?, P?) -> Unit)? = null): Resource<T, P> {
        return this.onSuccess { t, p ->
            hideProcessDialog()
            onSuccess?.invoke(t, p)
        }
    }

    open fun <T, P> Resource<T, P>.onDefaultLoading(
        processText: String? = "",
        onLoading: ((T?, P?) -> Unit)? = null
    ): Resource<T, P> {
        return this.onLoading { t, p ->
            showProcessDialog(processText)
            onLoading?.invoke(t, p)
        }
    }

    open fun <T, P> Resource<T, P>.onDefaultError(onError: ((Throwable?, T?, P?) -> Unit)? = null): Resource<T, P> {
        return this.onError { throwable, t, p ->
            hideProcessDialog()
            this.error?.message?.toast()
            onError?.invoke(throwable, t, p)
        }
    }

}