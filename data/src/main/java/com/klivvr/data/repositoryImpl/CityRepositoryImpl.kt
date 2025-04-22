package com.klivvr.data.repositoryImpl

import android.content.Context
import com.klivvr.data.dataSource.CityDataSource
import com.klivvr.data.mapper.toGroupedCities
import com.klivvr.domain.base.ApiResponse
import com.klivvr.domain.model.AllCitiesRequest
import com.klivvr.domain.model.QuerySearchRequest
import com.klivvr.domain.repositoryInterface.CityRepositoryInterface
import kotlinx.coroutines.delay


class CityRepositoryImpl(private val dataSource: CityDataSource, private val context: Context) :
    CityRepositoryInterface {
    override suspend fun invokeAllCities(request: AllCitiesRequest): ApiResponse<Any> =
        dataSource.loadAllData(context).let { cities ->
            delay(500)
            ApiResponse(data = cities.toGroupedCities(), status = cities.isNotEmpty())
        }


    override suspend fun invokeQuerySearch(request: QuerySearchRequest): ApiResponse<Any> =
         dataSource.search(request.query).let {cities ->
             delay(500)
             ApiResponse(data = cities.toGroupedCities(), status = cities.isNotEmpty())
         }
    }

