package com.githubsearcher.data.source.local

import com.githubsearcher.data.Users
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchLikeLocalDataSource(
    private val usersDao: UsersDao
) {

    fun likeUser(user: Users): Completable = Completable.fromCallable { usersDao.insertUser(user) }
        .subscribeOn(Schedulers.io())

    fun unlikeUser(
        id: String
    ): Completable = Completable.fromCallable { usersDao.deleteUser(id) }
        .subscribeOn(Schedulers.io())

    fun getLikeUsers(): Single<List<Users>> = Single.fromCallable { usersDao.getLikedUsers() }
        .subscribeOn(Schedulers.io())
}