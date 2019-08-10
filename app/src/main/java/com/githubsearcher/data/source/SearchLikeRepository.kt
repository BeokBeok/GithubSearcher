package com.githubsearcher.data.source

import com.githubsearcher.data.UsersDetail
import com.githubsearcher.data.source.remote.SearchLikeRemoteDataSource
import io.reactivex.disposables.Disposable

class SearchLikeRepository(
    private val searchLikeRemoteDataSource: SearchLikeRemoteDataSource
) : SearchLikeDataSource {

    override fun searchUserInfo(
        userID: String,
        onSuccess: (List<UsersDetail>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = searchLikeRemoteDataSource.searchUserInfo(
        userID,
        onSuccess,
        onFail
    )
}