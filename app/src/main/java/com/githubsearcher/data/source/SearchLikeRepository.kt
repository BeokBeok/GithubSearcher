package com.githubsearcher.data.source

import com.githubsearcher.data.Users
import com.githubsearcher.data.source.local.SearchLikeLocalDataSource
import com.githubsearcher.data.source.remote.SearchLikeRemoteDataSource
import io.reactivex.disposables.Disposable

class SearchLikeRepository(
    private val searchLikeLocalDataSource: SearchLikeLocalDataSource,
    private val searchLikeRemoteDataSource: SearchLikeRemoteDataSource
) : SearchLikeDataSource {

    override fun searchUserInfo(
        userID: String,
        onSuccess: (List<Users>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = searchLikeRemoteDataSource.searchUserInfo(
        userID,
        onSuccess,
        onFail
    )

    override fun likeUser(user: Users): Disposable =
        searchLikeLocalDataSource.likeUser(user)

    override fun unlikeUser(id: String): Disposable =
        searchLikeLocalDataSource.unlikeUser(id)

    override fun showLikeUsers(
        onSuccess: (List<Users>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = searchLikeLocalDataSource.showLikeUsers(
        onSuccess,
        onFail
    )
}