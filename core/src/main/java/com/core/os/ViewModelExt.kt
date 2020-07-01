package com.core.os

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.base.IRepository
import com.core.base.RepositoryViewModel

/**
 * Create by LiJie at 2019-05-29
 * 获取ViewModel
 */

/**
 * 获取普通ViewModel
 */
inline fun <reified T : ViewModel> FragmentActivity.viewModel(): T {
    return this.viewModels<T>().value
}

/**
 * 获取数据类ViewModel
 */
inline fun <reified T : RepositoryViewModel<R>, reified R : IRepository> FragmentActivity.repositoryViewModel(
    r: R
): T {
    return this.viewModels<T> { RepositoryModelFactory(R::class.java, r) }
        .value
}

/**
 * 获取普通ViewModel
 */
inline fun <reified T : ViewModel> Fragment.viewModel(): T {
    return this.viewModels<T>().value
}

/**
 * 获取数据类ViewModel
 */
inline fun <reified T : RepositoryViewModel<R>, reified R : IRepository> Fragment.repositoryViewModel(
    r: R
): T {
    return this.viewModels<T> { RepositoryModelFactory(R::class.java, r) }
        .value
}

/**
 * 数据类ViewModel的Factory
 */
class RepositoryModelFactory<R>(private val parameterTypes: Class<*>, private val r: R) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(parameterTypes).newInstance(r)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }

}