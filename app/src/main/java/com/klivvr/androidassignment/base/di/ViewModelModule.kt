package com.klivvr.androidassignment.base.di

import com.klivvr.androidassignment.viewModel.CityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module { viewModel { CityViewModel(useCase = get()) } }
