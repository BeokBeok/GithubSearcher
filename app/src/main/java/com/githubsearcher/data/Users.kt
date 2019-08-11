package com.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class Users(

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("score")
    val score: Double
)