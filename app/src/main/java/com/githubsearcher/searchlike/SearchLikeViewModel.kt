package com.githubsearcher.searchlike

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeRepository
import com.githubsearcher.util.RxEventBus

class SearchLikeViewModel(
    private val searchLikeRepository: SearchLikeRepository,
    private val id: Int
) : BaseViewModel() {

    private val _errMsg = MutableLiveData<Throwable>()
    private val _searchWord = MutableLiveData<String>()
    private var _page: Int = 1

    val users = MutableLiveData<List<Users>>()
    val errMsg: LiveData<Throwable> get() = _errMsg
    val searchWord: LiveData<String> get() = _searchWord
    val isLoading = MutableLiveData<Boolean>()

    fun searchUserInfo(userID: String) {
        if (id == SEARCH) {
            // Outline View Model -> Contents View Model
            RxEventBus.sendEvent(listOf<Users>())
        }
        _page = 1
        doSearchUser(userID)
    }

    fun searchNextUsers(userID: String) {
        _page += 1
        doSearchUser(userID)
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
        if (id == LIKE) {
            showLikeUser()
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

    private fun doSearchUser(userID: String) {
        isLoading.value = true
        RxEventBus.sendEvent(userID)
        addDisposable(
            searchLikeRepository.searchUserInfo(
                userID,
                _page,
                onSuccess = {
                    isLoading.value = false
                    users.value = it
                    if (id == SEARCH) {
                        // Outline View Model -> Contents View Model
                        RxEventBus.sendEvent(it)
                    }
                },
                onFail = {
                    isLoading.value = false
                    _errMsg.value = it
                }
            )
        )
    }

    companion object {
        private const val SEARCH = 0
        private const val LIKE = 1
    }
}