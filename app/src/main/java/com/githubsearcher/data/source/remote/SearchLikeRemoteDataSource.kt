package com.githubsearcher.data.source.remote

import com.githubsearcher.data.SearchLikeResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchLikeRemoteDataSource(
    private val retrofit: SearchLikeRemoteService
) {

    fun searchUserInfo(
        userID: String,
        page: Int,
        onSuccess: (SearchLikeResponse) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = retrofit.getUserInfo(
        userID,
        page
    )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            val response = it.body()
            if (it.isSuccessful && response != null) {
                onSuccess(response)
            } else {
                onFail(IllegalStateException("Please try again later"))
            }
        }, {
            onFail(it)
        })
}