package com.githubsearcher.searchlike

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.UsersDetail
import com.githubsearcher.data.source.SearchLikeRepository
import com.githubsearcher.util.RxEventBus

class SearchLikeViewModel(
    private val searchLikeRepository: SearchLikeRepository
) : BaseViewModel() {

    val usersDetail = MutableLiveData<List<UsersDetail>>()
    private val _errMsg = MutableLiveData<Throwable>()
    val errMsg: LiveData<Throwable> get() = _errMsg

    fun searchUserInfo(userID: String) {
        addDisposable(
            searchLikeRepository.searchUserInfo(
                userID,
                onSuccess = {
                    usersDetail.value = it
                    RxEventBus.sendEvent(it)
                },
                onFail = {
                    _errMsg.value = it
                }
            )
        )
    }
}