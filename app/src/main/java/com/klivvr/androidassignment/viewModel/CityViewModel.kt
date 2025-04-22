package com.klivvr.androidassignment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klivvr.androidassignment.base.viewState.UiViewState
import com.klivvr.androidassignment.utils.extention.saveApi
import com.klivvr.domain.model.AllCitiesRequest
import com.klivvr.domain.model.QuerySearchRequest
import com.klivvr.domain.usecase.CityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class CityViewModel(private val useCase: CityUseCase) : ViewModel() {
    private val citesMSF: MutableStateFlow<UiViewState?> = MutableStateFlow(null)
    val citiesSF: StateFlow<UiViewState?> = citesMSF

    private val searchResultMSF: MutableStateFlow<UiViewState?> = MutableStateFlow(null)
    val searchResultSF: StateFlow<UiViewState?> = searchResultMSF

    fun invokeAllCities(request: AllCitiesRequest) = viewModelScope.saveApi(
        citesMSF,
        request = request,
    ) { useCase.invokeAllCities(request) }

    fun invokeQuerySearch(request: QuerySearchRequest) = viewModelScope.saveApi(
        searchResultMSF,
        request = request,
    ) { useCase.invokeQuerySearch(request) }

}