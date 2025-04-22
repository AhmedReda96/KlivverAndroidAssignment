package com.klivvr.androidassignment.base.app

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.klivvr.androidassignment.base.di.dataSourceModule
import com.klivvr.androidassignment.base.di.repositoryModule
import com.klivvr.androidassignment.base.di.useCaseModule
import com.klivvr.androidassignment.base.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class BaseApp : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApp)
            modules(
                listOf(
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    dataSourceModule,
                )
            )
        }
    }
}

