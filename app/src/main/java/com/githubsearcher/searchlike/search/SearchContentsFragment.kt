package com.githubsearcher.searchlike.search

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.databinding.FragmentSearchContentsBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SearchContentsFragment :
    BaseFragment<FragmentSearchContentsBinding, SearchViewModel>(
        R.layout.fragment_search_contents
    ) {

    override val viewModel by sharedViewModel<SearchViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        initRecyclerView()
        setObserve()
    }

    private fun initRecyclerView() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = SearchAdapter(viewModel)
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