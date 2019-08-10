package com.githubsearcher.di

import com.githubsearcher.data.source.SearchLikeDataSource
import com.githubsearcher.data.source.remote.SearchLikeRemoteDataSource
import org.koin.dsl.module

val dataSourceModules = module {
    single<SearchLikeDataSource> { SearchLikeRemoteDataSource(get()) }
}