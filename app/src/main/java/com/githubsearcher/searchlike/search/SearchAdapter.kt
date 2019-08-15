package com.githubsearcher.searchlike.search

import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import com.githubsearcher.R
import com.githubsearcher.base.BaseRecyclerView
import com.githubsearcher.data.Users
import com.githubsearcher.databinding.RvUserSearchItemBinding

class SearchAdapter(
    private val vm: SearchViewModel
) : BaseRecyclerView.Adapter<Users, RvUserSearchItemBinding>(
    layoutRes,
    bindingVariableId
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerView.ViewHolder<RvUserSearchItemBinding> = ViewHolder(parent)

    inner class ViewHolder(
        parent: ViewGroup
    ) : BaseRecyclerView.ViewHolder<RvUserSearchItemBinding>(
        layoutRes,
        parent,
        bindingVariableId
    ) {

        init {
            binding.vm = vm
        }

        override fun onBindViewHolder(item: Any?) {
            super.onBindViewHolder(item)
            setCheckBoxListener(item as Users)
        }

        private fun setCheckBoxListener(users: Users) {
            with(binding.cbStar) {
                setOnCheckedChangeListener(null)
                isChecked = users.isLike
                setOnCheckedChangeListener { _, isChecked ->
                    users.isLike = isChecked
                }
            }
        }
    }

    companion object {
        private const val layoutRes = R.layout.rv_user_search_item
        private const val bindingVariableId = BR.users
    }
}