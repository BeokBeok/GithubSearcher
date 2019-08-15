package com.githubsearcher.searchlike

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.githubsearcher.searchlike.like.LikeContentsFragment
import com.githubsearcher.searchlike.search.SearchContentsFragment

class SearchLikeViewPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == LIKE) {
            LikeContentsFragment()
        } else {
            SearchContentsFragment()
        }
    }

    override fun getCount(): Int = MAX_PAGE

    override fun getItemPosition(`object`: Any): Int {
        return if (`object` is LikeContentsFragment) {
            PagerAdapter.POSITION_NONE
        } else {
            super.getItemPosition(`object`)
        }
    }

    companion object {
        private const val LIKE = 1
        private const val MAX_PAGE = 2
    }
}