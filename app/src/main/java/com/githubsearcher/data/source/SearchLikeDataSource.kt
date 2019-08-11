package com.githubsearcher.data.source

import com.githubsearcher.data.Users
import io.reactivex.disposables.Disposable

interface SearchLikeDataSource {
    fun searchUserInfo(
        userID: String,
        onSuccess: (List<Users>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable

    fun likeUser(user: Users): Disposable

    fun unlikeUser(id: String): Disposable

    fun showLikeUsers(
        onSuccess: (List<Users>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable
}