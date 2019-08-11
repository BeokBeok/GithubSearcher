package com.githubsearcher.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.githubsearcher.data.Users

@Database(
    entities = [Users::class],
    version = 1,
    exportSchema = false
)
abstract class SearchLikeDataBase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
}