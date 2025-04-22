package com.klivvr.domain.usecase

import com.klivvr.domain.model.AllCitiesRequest
import com.klivvr.domain.model.QuerySearchRequest
import com.klivvr.domain.repositoryInterface.CityRepositoryInterface

class CityUseCase(
    private val cityRepo: CityRepositoryInterface,
) {
    suspend fun invokeAllCities(request: AllCitiesRequest) = cityRepo.invokeAllCities(request)

    suspend fun invokeQuerySearch(request: QuerySearchRequest) = cityRepo.invokeQuerySearch(request)

}