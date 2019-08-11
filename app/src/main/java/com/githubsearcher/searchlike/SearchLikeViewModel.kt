package com.githubsearcher.searchlike

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeRepository
import com.githubsearcher.util.RxEventBus

class SearchLikeViewModel(
    private val searchLikeRepository: SearchLikeRepository
) : BaseViewModel() {

    private val _errMsg = MutableLiveData<Throwable>()
    private val _searchWord = MutableLiveData<String>()

    val users = MutableLiveData<List<Users>>()
    val errMsg: LiveData<Throwable> get() = _errMsg
    val searchWord: LiveData<String> get() = _searchWord


    fun searchUserInfo(userID: String) {
        addDisposable(
            searchLikeRepository.searchUserInfo(
                userID,
                onSuccess = {
                    users.value = it
                    // Outline View Model -> Contents View Model
                    RxEventBus.sendEvent(it)
                },
                onFail = {
                    _errMsg.value = it
                }
            )
        )
    }

    fun removeSearchWord() {
        _searchWord.value = ""
    }

    fun likeUser(
        user: Users,
        isChecked: Boolean
    ) {
        if (isChecked) {
            addDisposable(searchLikeRepository.likeUser(user))
        } else {
            addDisposable(searchLikeRepository.unlikeUser(user.login))
        }
    }

    fun showLikeUser() {
        addDisposable(searchLikeRepository.showLikeUsers(
            onSuccess = {
                users.value = it
            },
            onFail = {
                _errMsg.value = it
            }
        ))
    }
}