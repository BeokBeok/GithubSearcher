package com.githubsearcher.searchlike


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.data.Users
import com.githubsearcher.databinding.FragmentSearchLikeContentsBinding
import com.githubsearcher.util.RxEventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchLikeContentsFragment :
    BaseFragment<FragmentSearchLikeContentsBinding, SearchLikeViewModel>(
        R.layout.fragment_search_like_contents
    ) {

    private var searchWord: String = ""

    override val viewModel by viewModel<SearchLikeViewModel> { parametersOf(getPosition()) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        initRecyclerView()
        showContents()
        setObserve()
    }

    private fun initRecyclerView() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = SearchLikeAdapter(
                viewModel,
                getPosition()
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    super.onScrolled(
                        recyclerView,
                        dx,
                        dy
                    )
                    val lastVisibleItemPos =
                        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    if (getPosition() == 0 && lastVisibleItemPos + 1 == adapter?.itemCount) {
                        binding.vm?.searchNextUsers(searchWord)
                    }
                }
            })
        }
    }

    private fun showContents() {
        binding.vm?.let {
            val position = getPosition()
            if (position == 0) {
                it.addDisposable(
                    RxEventBus.getEvents()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ data ->
                            if (data is List<*>) {
                                @Suppress("UNCHECKED_CAST")
                                it.users.value = data as List<Users>
                            } else if (data is String) {
                                searchWord = data
                            }
                        }, { err ->
                            showToast(err.message)
                        })
                )
            } else if (position == 1) {
                it.showLikeUser()
            }
        }
    }

    private fun setObserve() {
        binding.vm?.let {
            it.errMsg.observe(
                this,
                Observer { throwable ->
                    showToast(throwable.message)
                }
            )
        }
    }

    private fun getPosition(): Int = arguments?.getInt("position") ?: 0
}
