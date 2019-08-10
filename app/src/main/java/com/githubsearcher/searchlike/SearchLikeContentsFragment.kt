package com.githubsearcher.searchlike


import android.os.Bundle
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.base.BaseRecyclerView
import com.githubsearcher.data.Users
import com.githubsearcher.databinding.FragmentSearchLikeContentsBinding
import com.githubsearcher.databinding.RvUserDetailItemBinding

class SearchLikeContentsFragment : BaseFragment<FragmentSearchLikeContentsBinding>(
    R.layout.fragment_search_like_contents
) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = object : BaseRecyclerView.Adapter<Users, RvUserDetailItemBinding>(
                R.layout.rv_user_detail_item,
                null
            ) {}
        }
    }
}
