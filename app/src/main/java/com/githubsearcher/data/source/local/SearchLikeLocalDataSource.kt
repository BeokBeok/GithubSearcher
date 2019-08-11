package com.githubsearcher.data.source.local

import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchLikeLocalDataSource(
    private val usersDao: UsersDao
) : SearchLikeDataSource {

    override fun searchUserInfo(
        userID: String,
        onSuccess: (List<Users>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = throw IllegalStateException("Not validate call")

    override fun likeUser(
        user: Users
    ): Disposable = Single.fromCallable {
        usersDao.insertUser(user)
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()

    override fun unlikeUser(
        id: String
    ): Disposable = Single.fromCallable {
        usersDao.deleteUser(id)
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()

    override fun showLikeUsers(
        onSuccess: (List<Users>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = Single.fromCallable {
        usersDao.getLikedUsers()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            onSuccess(it)
        }, {
            onFail(it)
        })
}