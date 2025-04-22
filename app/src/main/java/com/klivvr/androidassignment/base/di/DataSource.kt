package com.klivvr.androidassignment.base.di

import com.klivvr.data.dataSource.CityDataSource
import com.klivvr.data.dataSource.SearchTrieDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { SearchTrieDataSource() }
    single { CityDataSource(trie = get()) }
}

