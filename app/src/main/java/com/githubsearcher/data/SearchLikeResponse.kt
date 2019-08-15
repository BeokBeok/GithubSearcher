package com.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class SearchLikeResponse(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<Users>
)