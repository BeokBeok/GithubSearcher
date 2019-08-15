package com.githubsearcher.di

import com.githubsearcher.searchlike.like.LikeViewModel
import com.githubsearcher.searchlike.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { SearchViewModel(get()) }
    viewModel { LikeViewModel(get()) }
}