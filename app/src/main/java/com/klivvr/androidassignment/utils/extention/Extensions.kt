package com.klivvr.androidassignment.utils.extention

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import com.klivvr.androidassignment.base.viewState.UiViewState
import com.klivvr.domain.base.ApiResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import java.util.Locale

@Suppress("UNCHECKED_CAST")
fun CoroutineScope.saveApi(
    mutableState: MutableStateFlow<UiViewState?>,
    request: Any? = null,
    apiCall: suspend () -> Any
) {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        launch(Dispatchers.IO) {
            val errorResponse: ApiResponse<Any> = ApiResponse()
            errorResponse.status = false
            mutableState.emit(UiViewState.Error(request, errorResponse))
        }
    }

    launch(Dispatchers.IO + coroutineExceptionHandler) {
        mutableState.emit(UiViewState.Loading(request))
        val response: ApiResponse<Any> = apiCall.invoke() as ApiResponse<Any>
        if (response.status) mutableState.emit(UiViewState.Success(request, response))
        else mutableState.emit(UiViewState.Error(request, response))
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.openGoogleMapsApp(lat: Double, lng: Double) {
    val gmmIntentUri = "geo:$lat,$lng?q=$lat,$lng".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps") // Google Maps package

    if (mapIntent.resolveActivity(this.packageManager) != null) {
        this.startActivity(mapIntent)
    } else {
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            "https://www.google.com/maps/search/?api=1&query=$lat,$lng".toUri()
        )
        this.startActivity(webIntent)
    }
}

fun Context.hideKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow((this as? Activity)?.currentFocus?.windowToken, 0)
}