package com.core.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.core.R
import com.core.os.fullShow
import com.core.os.gone
import com.core.os.toast
import com.core.os.visible
import com.core.vo.Resource
import com.core.vo.onError
import com.core.vo.onLoading
import com.core.vo.onSuccess
import kotlinx.android.synthetic.main.layout_process.*

/**
 * Create by LiJie at 2019-05-29
 * BaseFragment，封装一些常用方法
 */
abstract class CoreFragment : Fragment() {

    //fragment布局文件
    protected abstract val layoutRes: Int

    //加载进度条
    private val processDialog by lazy {
        createProcessDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layoutRes != 0) {
            return inflater.inflate(layoutRes, container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置fragment背景，防止点击穿透或者透明问题
        view.background ?: run {
            val typedValue = TypedValue()
            context?.theme?.resolveAttribute(R.attr.background, typedValue, true)
            val color = typedValue.data
            if (color == 0) {
                ViewCompat.setBackground(
                    view,
                    ResourcesCompat.getDrawable(
                        resources,
                        android.R.color.background_light,
                        context?.theme
                    )
                )
            } else {
                ViewCompat.setBackground(
                    view,
                    ResourcesCompat.getDrawable(resources, color, context?.theme)
                )
            }
        }
    }

    /**
     * 显示进度条
     */
    protected open fun showProcessDialog(processText: String? = "加载中...") {
        if (processText.isNullOrEmpty()) {
            processDialog.process_text_view.gone()
        } else {
            processDialog.process_text_view.visible()
            processDialog.process_text_view?.text = processText
        }
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
        return Dialog(requireContext(), R.style.Dialog).apply {
            setContentView(
                LayoutInflater.from(context)
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

    /**
     * 返回按钮点击事件
     */
    open fun onBackPressed(): Boolean {
        return false
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