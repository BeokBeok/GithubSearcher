package com.githubsearcher.di

import androidx.room.Room
import com.githubsearcher.data.source.local.SearchLikeDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localServiceModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SearchLikeDataBase::class.java,
            "Users.db"
        ).build()
    }
    single { get<SearchLikeDataBase>().usersDao() }
}