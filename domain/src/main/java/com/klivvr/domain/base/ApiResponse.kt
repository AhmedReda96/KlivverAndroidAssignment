package com.klivvr.domain.base


open class ApiResponse<T>() {
    var status: Boolean = false
    var data: T? = null
    @Transient
    var request: Any? = null

    constructor(
        status: Boolean,
        data: T?,
    ) : this() {
        this.status = status
        this.data = data
    }
}
