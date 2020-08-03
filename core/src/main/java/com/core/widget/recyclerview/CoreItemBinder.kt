package com.core.widget.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.core.os.onClick
import com.drakeet.multitype.ItemViewBinder

abstract class CoreItemBinder<T> : ItemViewBinder<T, ViewHolder>() {

    var onItemClick: ((T) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, item: T) {
        with(holder.itemView) {
            onClick {
                onItemClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(onLayoutInflater(), parent, false))
    }

    abstract fun onLayoutInflater(): Int

}