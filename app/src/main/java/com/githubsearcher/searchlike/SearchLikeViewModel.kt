package com.githubsearcher.searchlike

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.data.Users
import com.githubsearcher.data.source.SearchLikeRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class SearchLikeViewModel(
    private val searchLikeRepository: SearchLikeRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<Users>>()
    val users: LiveData<List<Users>> get() = _users

    private val _searchWord = MutableLiveData<String>()
    val searchWord: LiveData<String> get() = _searchWord

    private val _errMsg = MutableLiveData<Throwable>()
    val errMsg: LiveData<Throwable> get() = _errMsg

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private var _currentPage = 1
    private var _totalPage = 1

    private val searchedUser = mutableListOf<Users>()

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
        addDisposable(
            searchLikeRepository.likeUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
        searchedUser[searchedUser.indexOf(user)].isLike = true
        _toastMsg.value = "You like ${user.login}"
    }

    fun unlikeUser(user: Users) {
        addDisposable(
            searchLikeRepository.unlikeUser(user.login)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
        searchedUser[searchedUser.indexOf(user)].isLike = false
        _toastMsg.value = "You unlike ${user.login}"
        showUser()
    }

    fun showUser() {
        if (currentTab == 1) {
            showLikeUser()
        } else {
            _users.value = searchedUser
        }
    }

    private fun showLikeUser() {
        addDisposable(
            searchLikeRepository.showLikeUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.map { user -> user.isLike = true }
                    _users.value = it
                }, {
                    _errMsg.value = it
                })
        )
    }

    private fun doSearchUser(userID: String) {
        if (currentTab == 1) return

        addDisposable(
            searchLikeRepository.searchUserInfo(userID, _currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.totalCount == 0) {
                        _errMsg.value = IllegalStateException("No results found")
                        searchedUser.clear()
                        _users.value = listOf()
                        return@subscribe
                    }
                    setTotalPage(it.totalCount)
                    if (it.items.isEmpty()) {
                        _errMsg.value = IllegalStateException("There is no more data to load")
                    } else {
                        if (_currentPage == 1) searchedUser.clear()
                        searchedUser.addAll(it.items)
                        _users.value = searchedUser
                    }
                }, {
                    _errMsg.value = it
                })
        )
    }

    private fun setTotalPage(totalCount: Int) {
        _totalPage = (totalCount + 30) / 30
    }
}