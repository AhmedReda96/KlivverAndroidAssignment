package com.klivvr.androidassignment.base.di

import com.klivvr.data.repositoryImpl.CityRepositoryImpl
import com.klivvr.domain.repositoryInterface.CityRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {
    single<CityRepositoryInterface> {
        CityRepositoryImpl(
            dataSource = get(), context = get()
        )
    }
}
