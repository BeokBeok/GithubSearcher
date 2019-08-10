package com.githubsearcher.searchlike

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

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

    override fun getCount(): Int = 2
}