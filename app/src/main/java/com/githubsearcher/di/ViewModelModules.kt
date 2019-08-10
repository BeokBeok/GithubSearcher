package com.githubsearcher.di

import com.githubsearcher.searchlike.SearchLikeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { SearchLikeViewModel(get(named("repo"))) }
}