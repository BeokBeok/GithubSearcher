package com.githubsearcher.searchlike

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class SearchLikeViewPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val searchLikeContentsFragment = SearchLikeContentsFragment()
        searchLikeContentsFragment.arguments = Bundle().apply {
            putInt("position", position)
        }
        return searchLikeContentsFragment
    }

    override fun getCount(): Int = MAX_PAGE

    override fun getItemPosition(`object`: Any): Int {
        if (`object` is SearchLikeContentsFragment) {
            if (`object`.arguments?.get("position") == 1) {
                return PagerAdapter.POSITION_NONE
            }
        }
        return super.getItemPosition(`object`)
    }

    companion object {
        private const val MAX_PAGE = 2
    }
}