package com.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class UsersDetail(

    // 아바타 이미지
    @SerializedName("avatar_url")
    val avatarUrl: String,

    // 이름
    @SerializedName("login")
    val login: String,

    // 스코프
    @SerializedName("score")
    val score: Double,

    // 좋아요 여부를 포함한 아이템
    @SerializedName("starred_url")
    val starredUrl: String
)