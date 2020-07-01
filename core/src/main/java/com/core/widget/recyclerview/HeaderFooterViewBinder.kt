package com.core.widget.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.drakeet.multitype.ItemViewBinder

/**
 * Create by LiJie at 2019-05-29
 * Header和Footer的item样式
 */
class HeaderFooterViewBinder(private val recyclerView: RecyclerView) : ItemViewBinder<View, ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val frameLayout = FrameLayout(parent.context)
        val layoutParams: RecyclerView.LayoutParams =
            RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        frameLayout.layoutParams = layoutParams
        return ViewHolder(frameLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: View) {
        val frameLayout = holder.itemView as FrameLayout
        val parent = item.parent
        if (parent != null) {
            val viewGroup = parent as ViewGroup
            viewGroup.removeView(item)
        }
        frameLayout.removeAllViews()
        frameLayout.addView(item, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is StaggeredGridLayoutManager) {
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = true
            }
        }
    }

}