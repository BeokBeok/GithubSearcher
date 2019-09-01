package com.githubsearcher.searchlike

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeRepository

class SearchLikeViewModel(
    private val searchLikeRepository: SearchLikeRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<Users>>()
    private val _searchWord = MutableLiveData<String>()

    private val _errMsg = MutableLiveData<Throwable>()
    private val _toastMsg = MutableLiveData<String>()

    private val _searchedUsers = mutableListOf<Users>()
    private val _likedUsers = mutableListOf<Users>()

    private var _currentPage = 1
    private var _totalPage = 1

    val users: LiveData<List<Users>> get() = _users
    val searchWord: LiveData<String> get() = _searchWord

    val errMsg: LiveData<Throwable> get() = _errMsg
    val toastMsg: LiveData<String> get() = _toastMsg

    var currentTab: Int = 0

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
        val removeLikeUser = _searchedUsers.asSequence()
            .filterNot { likeUser -> likeUser.login == user.login }
            .toList()
        _searchedUsers.clear()
        _searchedUsers.addAll(removeLikeUser)
        _users.value = _searchedUsers
        _toastMsg.value = "You like ${user.login}"
    }

    fun unlikeUser(user: Users) {
        addDisposable(searchLikeRepository.unlikeUser(user.login))
        _toastMsg.value = "You unlike ${user.login}"
        showLikeUser()
    }

    fun showUsers() = if (currentTab == 1) {
        showLikeUser()
    } else {
        _users.value = _searchedUsers
    }

    private fun showLikeUser() {
        addDisposable(searchLikeRepository.showLikeUsers(
            onSuccess = {
                _likedUsers.clear()
                _likedUsers.addAll(it)
                _users.value = _likedUsers
            },
            onFail = {
                _errMsg.value = it
            }
        ))
    }

    private fun doSearchUser(userID: String) {
        if (currentTab == 1) {
            return
        }
        addDisposable(
            searchLikeRepository.searchUserInfo(
                userID,
                _currentPage,
                onSuccess = {
                    if (it.totalCount == 0) {
                        _errMsg.value = IllegalStateException("No results found")
                        _searchedUsers.clear()
                        _users.value = _searchedUsers
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
                if (_currentPage == 1) {
                    _searchedUsers.clear()
                }
                _searchedUsers.addAll(
                    users.asSequence()
                        .filterNot { users ->
                            likeUsers.any { likeUser ->
                                likeUser.login == users.login
                            }
                        }
                        .toList()
                )
                _users.value = _searchedUsers
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