package com.githubsearcher.searchlike.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeRepository

class SearchViewModel(
    private val searchLikeRepository: SearchLikeRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<Users>>()
    private val _errMsg = MutableLiveData<Throwable>()
    private val _searchWord = MutableLiveData<String>()
    private var _totalPage = 1

    val users: LiveData<List<Users>> get() = _users
    val errMsg: LiveData<Throwable> get() = _errMsg
    val searchWord: LiveData<String> get() = _searchWord

    var currentPage = 1

    fun searchUserInfo(userID: String) {
        currentPage = 1
        _searchWord.value = userID
        doSearchUser(userID)
    }

    fun searchNextUsers() {
        currentPage += 1
        if (currentPage > _totalPage) {
            currentPage = _totalPage
            return
        }
        searchWord.value?.let { doSearchUser(it) }
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

    private fun doSearchUser(userID: String) {
        searchLikeRepository.searchUserInfo(
            userID,
            currentPage,
            onSuccess = {
                setTotalPage(it.totalCount)
                if (it.items.isEmpty()) {
                    _errMsg.value = IllegalStateException("There is no more data to load")
                } else {
                    _users.value = it.items
                }
            },
            onFail = {
                _errMsg.value = it
            }
        )
    }

    private fun setTotalPage(totalCount: Int) {
        _totalPage = (totalCount + 30) / 30
    }
}