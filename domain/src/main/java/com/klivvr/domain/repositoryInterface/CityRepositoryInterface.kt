package com.klivvr.domain.repositoryInterface

import com.klivvr.domain.base.ApiResponse
import com.klivvr.domain.model.AllCitiesRequest
import com.klivvr.domain.model.QuerySearchRequest

interface CityRepositoryInterface {
    suspend fun invokeAllCities(request: AllCitiesRequest): ApiResponse<Any>
    suspend fun invokeQuerySearch(request: QuerySearchRequest): ApiResponse<Any>
}