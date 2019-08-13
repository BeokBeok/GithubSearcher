package com.githubsearcher.di

import com.githubsearcher.data.source.SearchLikeRepository
import com.githubsearcher.data.source.local.SearchLikeLocalDataSource
import com.githubsearcher.data.source.remote.SearchLikeRemoteDataSource
import org.koin.dsl.module

val dataSourceModules = module {
    single { SearchLikeRepository(get(), get()) }
    single { SearchLikeLocalDataSource(get()) }
    single { SearchLikeRemoteDataSource(get()) }
}