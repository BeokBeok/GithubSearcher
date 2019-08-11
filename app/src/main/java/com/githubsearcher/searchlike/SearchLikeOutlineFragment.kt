package com.githubsearcher.searchlike


import android.os.Bundle
import android.view.inputmethod.EditorInfo
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
                        tab?.let {
                            vpContents.currentItem = it.position

                            if (it.position == 1) {
                                vpContents.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                })
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

    private fun setKeypadEditorActionListener() {
        binding.etSearch.run {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.vm?.searchUserInfo(textView.text.toString())
                    false
                } else {
                    true
                }
            }
        }
    }
}
