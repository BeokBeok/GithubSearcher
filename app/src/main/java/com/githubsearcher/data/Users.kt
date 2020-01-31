package com.githubsearcher.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Users")
data class Users(

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @PrimaryKey
    @SerializedName("login")
    val login: String,

    @SerializedName("score")
    val score: Double,

    var isLike: Boolean
)