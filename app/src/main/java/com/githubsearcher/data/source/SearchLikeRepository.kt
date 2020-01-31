package com.githubsearcher.data.source

import com.githubsearcher.data.SearchLikeResponse
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.local.SearchLikeLocalDataSource
import com.githubsearcher.data.source.remote.SearchLikeRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class SearchLikeRepository(
    private val searchLikeLocalDataSource: SearchLikeLocalDataSource,
    private val searchLikeRemoteDataSource: SearchLikeRemoteDataSource
) {

    fun searchUserInfo(userID: String, page: Int): Single<SearchLikeResponse> =
        searchLikeRemoteDataSource.searchUserInfo(userID, page)
            .zipWith(
                searchLikeLocalDataSource.getLikeUsers(),
                BiFunction { remoteUser, likeUser ->
                    SearchLikeResponse(
                        remoteUser.totalCount,
                        remoteUser.items.map { users ->
                            users.isLike = likeUser.any { likeUser ->
                                likeUser.login == users.login
                            }
                            users
                        }
                    )
                })

    fun likeUser(user: Users): Completable =
        searchLikeLocalDataSource.likeUser(user)

    fun unlikeUser(id: String): Completable =
        searchLikeLocalDataSource.unlikeUser(id)

    fun showLikeUsers(): Single<List<Users>> = searchLikeLocalDataSource.getLikeUsers()
}