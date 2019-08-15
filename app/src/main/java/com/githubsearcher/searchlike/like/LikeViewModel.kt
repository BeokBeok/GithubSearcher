package com.githubsearcher.searchlike.like

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeRepository

class LikeViewModel(
    private val searchLikeRepository: SearchLikeRepository
) : BaseViewModel() {

    private val _errMsg = MutableLiveData<Throwable>()
    private val _users = MutableLiveData<List<Users>>()

    val users: LiveData<List<Users>> get() = _users
    val errMsg: LiveData<Throwable> get() = _errMsg
    val page = 1

    fun likeUser(
        user: Users,
        isChecked: Boolean
    ) {
        if (isChecked) {
            addDisposable(searchLikeRepository.likeUser(user))
        } else {
            addDisposable(searchLikeRepository.unlikeUser(user.login))
        }
        showLikeUser()
    }

    fun showLikeUser() {
        addDisposable(searchLikeRepository.showLikeUsers(
            onSuccess = {
                _users.value = it
            },
            onFail = {
                _errMsg.value = it
            }
        ))
    }
}