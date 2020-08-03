package com.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.repository.IRepository
import com.core.vo.ResLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Create by LiJie at 2019-05-27
 * 包含数据层的ViewModel
 */
open class RepositoryViewModel<out T : IRepository>(protected val repository: T) : ViewModel() {

    protected inline fun <T> launch(
        resLiveData: ResLiveData<T>? = null,
        crossinline block: suspend () -> T?
    ) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    resLiveData?.loading(resLiveData.progress)
                    val t = withContext(Dispatchers.IO) {
                        block()
                    }
                    resLiveData?.success(t)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                resLiveData?.error(e)
            }
        }
    }

}