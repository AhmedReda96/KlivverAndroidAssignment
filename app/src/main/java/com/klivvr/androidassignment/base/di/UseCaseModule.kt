package com.klivvr.androidassignment.base.di

import com.klivvr.androidassignment.view.screens.CityListScreen
import com.klivvr.domain.usecase.CityUseCase
import org.koin.dsl.module

val useCaseModule = module { single { CityUseCase(get()) }
    single { CityListScreen(get()) }}