package com.githubsearcher.searchlike.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeRepository

class SearchViewModel(
    private val searchLikeRepository: SearchLikeRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<MutableList<Users>>()
        .apply { value = mutableListOf() }
    private val _errMsg = MutableLiveData<Throwable>()
    private val _searchWord = MutableLiveData<String>()
    private val _toastMsg = MutableLiveData<String>()

    private var _currentPage = 1
    private var _totalPage = 1

    val users: LiveData<MutableList<Users>> get() = _users
    val errMsg: LiveData<Throwable> get() = _errMsg
    val searchWord: LiveData<String> get() = _searchWord
    val toastMsg: LiveData<String> get() = _toastMsg

    fun searchUserInfo(userID: String) {
        _currentPage = 1
        _searchWord.value = userID
        doSearchUser(userID)
    }

    fun searchNextUsers() {
        _currentPage += 1
        if (_currentPage > _totalPage) {
            _currentPage = _totalPage
            return
        }
        searchWord.value?.let { doSearchUser(it) }
    }

    fun removeSearchWord() {
        _searchWord.value = ""
    }

    fun likeUser(user: Users) {
        addDisposable(searchLikeRepository.likeUser(user))
        val removeLikeUsers = mutableListOf<Users>()
        _users.value?.let {
            removeLikeUsers.addAll(
                it.asSequence()
                    .filterNot { likeUser ->
                        likeUser.login == user.login
                    }
                    .toList()
            )
        }
        _users.value = removeLikeUsers
        _toastMsg.value = "You like ${user.login}"
    }

    private fun doSearchUser(userID: String) {
        addDisposable(
            searchLikeRepository.searchUserInfo(
                userID,
                _currentPage,
                onSuccess = {
                    if (it.totalCount == 0) {
                        _errMsg.value = IllegalStateException("No results found")
                        _users.value = mutableListOf()
                    } else {
                        setTotalPage(it.totalCount)
                        if (it.items.isEmpty()) {
                            _errMsg.value = IllegalStateException("There is no more data to load")
                        } else {
                            doCheckLikeUser(it.items)
                        }
                    }
                },
                onFail = {
                    _errMsg.value = it
                }
            )
        )
    }

    private fun doCheckLikeUser(users: List<Users>) {
        addDisposable(searchLikeRepository.showLikeUsers(
            onSuccess = { likeUsers ->
                val removeLikeUsers = mutableListOf<Users>()
                _users.value?.let {
                    if (_currentPage == 1) {
                        it.clear()
                    }
                    removeLikeUsers.addAll(it)
                    removeLikeUsers.addAll(
                        users.asSequence()
                            .filterNot { users ->
                                likeUsers.any { likeUser ->
                                    likeUser.login == users.login
                                }
                            }
                            .toList()
                    )
                }
                _users.value = removeLikeUsers
            },
            onFail = {
                _errMsg.value = it
            }
        ))
    }

    private fun setTotalPage(totalCount: Int) {
        _totalPage = (totalCount + 30) / 30
    }
}