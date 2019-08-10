package com.githubsearcher.common

import android.annotation.SuppressLint
import android.app.Application
import com.githubsearcher.di.dataSourceModules
import com.githubsearcher.di.getRemoteServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@SuppressLint("unused")
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(
                listOf(
                    dataSourceModules,
                    getRemoteServiceModule("https://api.github.com")
                )
            )
        }
    }
}