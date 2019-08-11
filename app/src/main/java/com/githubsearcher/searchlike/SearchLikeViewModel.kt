package com.githubsearcher.searchlike

import android.util.Log
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
        Log.d("kkk", "isCheck is $isChecked")
    }
}