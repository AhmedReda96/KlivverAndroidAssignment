package com.klivvr.androidassignment.view.baseViews

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.klivvr.androidassignment.base.viewState.UiViewState
import com.klivvr.domain.base.ApiResponse

open class BaseScreen {
    protected var isLoading = mutableStateOf(false)

    protected fun handleUI(
        viewState: UiViewState?
    ) {
        println("test- UiViewState ${viewState?.request}")

        when (viewState) {
            is UiViewState.Loading<*> -> showLoader(viewState)
            is UiViewState.Error<*> -> hideLoader()
            is UiViewState.Success<*> -> with(viewState) {
                hideLoader()
                (item as ApiResponse<*>).request = this.request
                handleResponse(item)
            }

            else -> Log.d("UNKNOWN_VIEW_STATE", this.javaClass.simpleName)
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun handleResponse(response: ApiResponse<*>) {
    }

    protected fun hideLoader() { isLoading.value = false }

    protected fun showLoader(viewState: UiViewState.Loading<*>) {
    println("test- ${viewState.request}")
        isLoading.value = true

    }
}