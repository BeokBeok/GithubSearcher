package com.githubsearcher.di

import com.githubsearcher.data.source.SearchLikeDataSource
import com.githubsearcher.data.source.SearchLikeRepository
import com.githubsearcher.data.source.local.SearchLikeLocalDataSource
import com.githubsearcher.data.source.remote.SearchLikeRemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModules = module {
    single<SearchLikeDataSource>(named("repo")) {
        SearchLikeRepository(get(named("local")), get(named("remote")))
    }
    single<SearchLikeDataSource>(named("local")) {
        SearchLikeLocalDataSource(get())
    }
    single<SearchLikeDataSource>(named("remote")) {
        SearchLikeRemoteDataSource(get())
    }
}