package com.core.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

    protected open var contentId = 0
    private var current: Fragment? = null

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

    public fun addContent(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            add(contentId, fragment)
            if (addToBackStack) {
                addToBackStack(fragment.javaClass.simpleName)
            }
            current = fragment
        }.commitAllowingStateLoss()
    }

    public fun replaceContent(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(contentId, fragment)
            if (addToBackStack) {
                addToBackStack(fragment.javaClass.simpleName)
            }
            current = fragment
        }.commitAllowingStateLoss()
    }

    public fun switchContent(target: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            current?.let {
                hide(it)
            }
            if (target.isAdded) {
                show(target)
            } else {
                add(contentId, target)
                if (addToBackStack) {
                    addToBackStack(target.javaClass.simpleName)
                }
            }
            current = target
        }.commitAllowingStateLoss()
    }

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
        supportFragmentManager.fragments.reversed().forEach { fragment ->
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
        arrayListOf<String>().forEachIndexed { index, s -> }
    }

    open fun <T> Resource<T>.onDefaultSuccess(onSuccess: ((T?) -> Unit)? = null): Resource<T> {
        return this.onSuccess { t ->
            hideProcessDialog()
            onSuccess?.invoke(t)
        }
    }

    open fun <T> Resource<T>.onDefaultLoading(
        processText: String? = "",
        onLoading: ((Int?) -> Unit)? = null
    ): Resource<T> {
        return this.onLoading { p ->
            showProcessDialog(processText)
            onLoading?.invoke(p)
        }
    }

    open fun <T> Resource<T>.onDefaultError(onError: ((Throwable?) -> Unit)? = null): Resource<T> {
        return this.onError { throwable ->
            hideProcessDialog()
            this.error?.message?.toast()
            onError?.invoke(throwable)
        }
    }

}