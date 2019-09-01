package com.githubsearcher.searchlike

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.data.Users
import com.githubsearcher.databinding.FragmentSearchLikeContentsBinding
import com.githubsearcher.databinding.RvUserSearchLikeItemBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SearchLikeContentsFragment :
    BaseFragment<FragmentSearchLikeContentsBinding, SearchLikeViewModel>(
        R.layout.fragment_search_like_contents
    ) {

    override val viewModel by sharedViewModel<SearchLikeViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        initRecyclerView()
        setObserve()
    }

    private fun initRecyclerView() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = SearchLikeAdapter<Users, RvUserSearchLikeItemBinding>(
                R.layout.rv_user_search_like_item,
                BR.users,
                viewModel
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
                    if (arguments?.getInt("position", 0) == 1) {
                        return
                    }
                    val lastVisibleItemPos =
                        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    if (lastVisibleItemPos + 1 == adapter?.itemCount) {
                        binding.vm?.searchNextUsers()
                    }
                }
            })
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
            it.toastMsg.observe(
                this,
                Observer { msg ->
                    showToast(msg)
                }
            )
        }
    }
}