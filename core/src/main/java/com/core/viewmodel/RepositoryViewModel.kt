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

    protected inline fun <T, P> launch(
        resLiveData: ResLiveData<T, P>? = null,
        crossinline block: suspend () -> T?
    ) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    resLiveData?.loading(resLiveData.progress, resLiveData.data)
                    val t = withContext(Dispatchers.IO) {
                        block()
                    }
                    resLiveData?.success(t, resLiveData.progress)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                resLiveData?.error(e, resLiveData.data, resLiveData.progress)
            }
        }
    }

}