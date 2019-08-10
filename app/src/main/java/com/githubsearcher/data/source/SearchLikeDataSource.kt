package com.githubsearcher.data.source

import com.githubsearcher.data.UsersDetail
import io.reactivex.disposables.Disposable

interface SearchLikeDataSource {
    fun searchUserInfo(
        userID: String,
        onSuccess: (List<UsersDetail>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable
}