package com.githubsearcher.searchlike


import android.os.Bundle
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.data.Users
import com.githubsearcher.databinding.FragmentSearchLikeContentsBinding
import com.githubsearcher.util.RxEventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel

class SearchLikeContentsFragment :
    BaseFragment<FragmentSearchLikeContentsBinding, SearchLikeViewModel>(
        R.layout.fragment_search_like_contents
    ) {

    override val viewModel by viewModel<SearchLikeViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        initRecyclerView()
        showContents()
    }

    private fun initRecyclerView() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = SearchLikeAdapter(viewModel)
        }
    }

    @Suppress("UNCHECKED_CAST")
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
                                it.users.value = data as List<Users>
                            }
                        }, { err ->
                            showToast(err.message)
                        })
                )
            }
        }
    }

    private fun getPosition(): Int = arguments?.getInt("position") ?: 0
}
