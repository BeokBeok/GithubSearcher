package com.githubsearcher.data.source.remote

import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchLikeRemoteDataSource(
    private val retrofit: SearchLikeRemoteService
) : SearchLikeDataSource {
    override fun getSearchedUserInfo(
        onSuccess: (Users) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = retrofit.getUserInfo()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({

        }, {

        })
}