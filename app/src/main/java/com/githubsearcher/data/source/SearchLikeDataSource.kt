package com.githubsearcher.data.source

import com.githubsearcher.data.Users
import io.reactivex.disposables.Disposable

interface SearchLikeDataSource {
    fun getSearchedUserInfo(
        onSuccess: (Users) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable
}