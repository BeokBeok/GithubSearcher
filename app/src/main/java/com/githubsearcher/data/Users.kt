package com.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class Users(

    @SerializedName("items")
    val items: List<UsersDetail>
)