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
    private val _toastMsg = MutableLiveData<String>()

    val users: LiveData<List<Users>> get() = _users
    val errMsg: LiveData<Throwable> get() = _errMsg
    val toastMsg: LiveData<String> get() = _toastMsg

    fun unlikeUser(user: Users) {
        addDisposable(searchLikeRepository.unlikeUser(user.login))
        _toastMsg.value = "You unlike ${user.login}"
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