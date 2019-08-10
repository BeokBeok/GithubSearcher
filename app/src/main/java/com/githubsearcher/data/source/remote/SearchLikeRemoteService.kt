package com.githubsearcher.data.source.remote

import com.githubsearcher.data.Users
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchLikeRemoteService {
    @GET("/search/users")
    fun getUserInfo(
        @Query("q")
        userID: String
    ): Single<Response<Users>>
}