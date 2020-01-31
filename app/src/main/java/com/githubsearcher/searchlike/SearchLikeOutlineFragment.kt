package com.githubsearcher.searchlike


import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.githubsearcher.R
import com.githubsearcher.base.BaseFragment
import com.githubsearcher.common.TabSelectedListener
import com.githubsearcher.databinding.FragmentSearchLikeOutlineBinding
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SearchLikeOutlineFragment :
    BaseFragment<FragmentSearchLikeOutlineBinding, SearchLikeViewModel>(
        R.layout.fragment_search_like_outline
    ) {

    override val viewModel by sharedViewModel<SearchLikeViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        initViewPager()
        setObserve()
        setKeypadEditorActionListener()
    }

    private fun initViewPager() {
        binding.run {

            with(vpContents) {
                adapter = fragmentManager?.let { SearchLikeViewPagerAdapter(it) }
                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tlSearchLike))
            }

            with(tlSearchLike) {
                addTab(newTab().setText(R.string.search))
                addTab(newTab().setText(R.string.like))
                addOnTabSelectedListener(object : TabSelectedListener() {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab == null) return
                        tab.position.let {
                            vpContents.currentItem = it
                            vm?.currentTab = it
                            vm?.showUser()
                        }
                    }
                })
            }

        }
    }

    private fun setObserve() {
        viewModel.errMsg.observe(
            viewLifecycleOwner,
            Observer { throwable ->
                showToast(throwable.message)
            }
        )
    }

    private fun setKeypadEditorActionListener() {
        binding.etSearch.run {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.searchUserInfo(textView.text.toString())
                    false
                } else {
                    true
                }
            }
        }
    }
}
