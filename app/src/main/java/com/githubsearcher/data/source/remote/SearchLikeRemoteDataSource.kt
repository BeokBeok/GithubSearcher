package com.githubsearcher.data.source.remote

import com.githubsearcher.data.SearchLikeResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchLikeRemoteDataSource(
    private val retrofit: SearchLikeRemoteService
) {

    fun searchUserInfo(userID: String, page: Int): Single<SearchLikeResponse> =
        retrofit.getUserInfo(userID, page)
            .subscribeOn(Schedulers.io())
}