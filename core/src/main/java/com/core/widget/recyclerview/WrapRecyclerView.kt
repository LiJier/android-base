package com.core.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.MultiTypeAdapter
import com.drakeet.multitype.OneToManyFlow

/**
 * Create by LiJie at 2019-05-29
 * 封装的RecycleView，可以添加header、footer，默认LinearLayoutManager
 */
class WrapRecyclerView : RecyclerView {

    //使用的adapter
    private val adapter: MultiTypeAdapter = MultiTypeAdapter()
    //设置emptyView
    var emptyView: View? = null
        set(value) {
            field = value
            notifyItems()
        }
    //设置显示的item list
    var items = listOf<Any>()
        set(value) {
            field = value
            notifyItems()
        }
    //是否使用diffUtil
    var userDiffUtil = true
    //header
    private val headerList = mutableListOf<View>()
    //footer
    private val footerList = mutableListOf<View>()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    /**
     * 初始化
     */
    private fun init() {
        //注册header、footer itemBinder
        adapter.register(View::class.java, HeaderFooterViewBinder(this))
        //默认LinearLayoutManager
        layoutManager = LinearLayoutManager(context)
        //默认使用全局emptyView
        emptyView = defaultEmptyLayoutCreator?.invoke()
    }

    /**
     * 设置LayoutManager
     */
    override fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
        //设置为GridLayoutManager时header、footer占满行
        if (layoutManager is GridLayoutManager) {
            val spanSizeLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = adapter.items[position]
                    return if (viewType is View) {
                        layoutManager.spanCount
                    } else {
                        spanSizeLookup?.getSpanSize(position) ?: 1
                    }
                }
            }
        }
        super.setLayoutManager(layoutManager)
    }

    /**
     * 注册实体对应的itemBinder，一对一
     */
    fun <T> register(clazz: Class<T>, binder: ItemViewBinder<T, *>) {
        adapter.register(clazz, binder)
    }

    /**
     * 注册实体对应的itemBinder，一对多
     */
    fun <T> register(clazz: Class<T>): OneToManyFlow<T> {
        return adapter.register(clazz)
    }

    /**
     * 添加header
     */
    fun addHeader(view: View) {
        headerList.add(view)
        notifyItems()
    }

    /**
     * 添加footer
     */
    fun addFooter(view: View) {
        footerList.add(view)
        notifyItems()
    }

    /**
     * 获取header个数
     */
    fun getHeaderCount(): Int = headerList.size

    /**
     * 获取footer个数
     */
    fun getFooterCount(): Int = footerList.size

    /**
     * 更新数据
     */
    private fun notifyItems() {
        val allItem = mutableListOf<Any>()
        allItem.addAll(headerList)
        if (items.isEmpty()) {
            emptyView?.let { allItem.add(it) }
        }
        allItem.addAll(items)
        allItem.addAll(footerList)
        getAdapter()?.let {
            if (userDiffUtil) {
                val oldList = (it as MultiTypeAdapter).items
                val diffResult = DiffUtil.calculateDiff(DiffCallback(oldList, allItem), true)
                adapter.items = allItem
                diffResult.dispatchUpdatesTo(it)
            } else {
                adapter.items = allItem
                adapter.notifyDataSetChanged()
            }
        } ?: run {
            adapter.items = allItem
            setAdapter(adapter)
        }
    }

    companion object {
        //全局emptyView
        var defaultEmptyLayoutCreator: (() -> View)? = null
    }

}