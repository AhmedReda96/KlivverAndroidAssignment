package com.klivvr.androidassignment.base.viewState

sealed class UiViewState {
    var request: Any? = null

    open class Loading<T : Any> : UiViewState {
        constructor(request: T?) : super() {
            this.request = request
        }
    }

    class Success<T>(val item: T) : UiViewState() where T : Any {
        constructor(request: T?, response: T) : this(response) {
            this.request = request
        }
    }

    class Error<T>(val item: T) : UiViewState() where T : Any {
        constructor(
            request: T?,
            response: T,
        ) : this(response) {
            this.request = request
        }
    }
}