package com.githubsearcher.data.source.remote

import com.githubsearcher.data.Users
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchLikeRemoteDataSource(
    private val retrofit: SearchLikeRemoteService
) {

    fun searchUserInfo(
        userID: String,
        onSuccess: (List<Users>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = retrofit.getUserInfo(userID)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            val users = it.body()
            if (it.isSuccessful && users != null) {
                val usersInfo = users.items
                onSuccess(usersInfo)
            } else {
                onFail(IllegalStateException("Data is null"))
            }
        }, {
            onFail(it)
        })
}