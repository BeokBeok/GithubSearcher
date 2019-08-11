package com.githubsearcher.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.githubsearcher.data.Users

@Dao
interface UsersDao {
    @Query("SELECT * FROM Users")
    fun getLikedUsers(): List<Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: Users)

    @Query("DELETE FROM Users WHERE login = :id")
    fun deleteUser(id: String)
}