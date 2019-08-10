package com.githubsearcher.searchlike


import android.os.Bundle
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.common.TabSelectedListener
import com.githubsearcher.databinding.FragmentSearchLikeOutlineBinding
import com.google.android.material.tabs.TabLayout

class SearchLikeOutlineFragment : BaseFragment<FragmentSearchLikeOutlineBinding>(
    R.layout.fragment_search_like_outline
) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        with(binding.vpContents) {
            adapter = fragmentManager?.let { SearchLikeViewPagerAdapter(it) }
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tlSearchLike))
        }

        with(binding.tlSearchLike) {
            addTab(newTab().setText(R.string.search))
            addTab(newTab().setText(R.string.like))
            addOnTabSelectedListener(object : TabSelectedListener() {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        binding.vpContents.currentItem = it.position
                    }
                }
            })
        }
    }
}
