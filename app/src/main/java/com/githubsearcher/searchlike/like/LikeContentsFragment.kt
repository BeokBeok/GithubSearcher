package com.githubsearcher.searchlike.like

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.data.Users
import com.githubsearcher.databinding.FragmentLikeContentsBinding
import com.githubsearcher.databinding.RvUserLikeItemBinding
import com.githubsearcher.searchlike.SearchLikeAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class LikeContentsFragment :
    BaseFragment<FragmentLikeContentsBinding, LikeViewModel>(
        R.layout.fragment_like_contents
    ) {

    override val viewModel by viewModel<LikeViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        showContents()
        setObserve()
    }

    private fun initRecyclerView() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter =
                SearchLikeAdapter<Users, RvUserLikeItemBinding>(
                    R.layout.rv_user_like_item,
                    BR.users,
                    viewModel
                )
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

    private fun showContents() {
        binding.vm = viewModel
        binding.vm?.showLikeUser()
    }
}