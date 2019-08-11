package com.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class SearchLikeResponse(

    @SerializedName("items")
    val items: List<Users>
)