package com.githubsearcher.searchlike


import android.os.Bundle
import androidx.lifecycle.Observer
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.common.TabSelectedListener
import com.githubsearcher.databinding.FragmentSearchLikeOutlineBinding
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.viewModel

class SearchLikeOutlineFragment :
    BaseFragment<FragmentSearchLikeOutlineBinding, SearchLikeViewModel>(
        R.layout.fragment_search_like_outline
    ) {
    override val viewModel by viewModel<SearchLikeViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        initViewPager()
        setObserve()
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

    private fun setObserve() {
        binding.vm?.let {
            it.errMsg.observe(
                this,
                Observer {
                    showToast(it.message)
                }
            )
        }
    }
}
