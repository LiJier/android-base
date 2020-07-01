package com.core.widget.recyclerview

import androidx.recyclerview.widget.DiffUtil

/**
 * Create by LiJie at 2019-05-29
 * 封装的RecycleView默认使用的DiffCallback
 */
class DiffCallback(private val oldList: List<*>, private val newList: List<*>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}