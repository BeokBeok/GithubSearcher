package com.githubsearcher.searchlike

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.githubsearcher.base.BaseRecyclerView
import com.githubsearcher.base.BaseViewModel
import com.githubsearcher.databinding.RvUserLikeItemBinding
import com.githubsearcher.databinding.RvUserSearchItemBinding
import com.githubsearcher.searchlike.like.LikeViewModel
import com.githubsearcher.searchlike.search.SearchViewModel

class SearchLikeAdapter<A : Any, VDB : ViewDataBinding>(
    @LayoutRes
    private val layoutRes: Int,
    private val bindingVariableId: Int,
    private val vm: BaseViewModel
) : BaseRecyclerView.Adapter<A, VDB>(
    layoutRes,
    bindingVariableId
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerView.ViewHolder<VDB> = ViewHolder(parent)

    inner class ViewHolder(
        parent: ViewGroup
    ) : BaseRecyclerView.ViewHolder<VDB>(
        layoutRes,
        parent,
        bindingVariableId
    ) {

        init {
            if (binding is RvUserSearchItemBinding) {
                binding.vm = vm as SearchViewModel
            } else if (binding is RvUserLikeItemBinding) {
                binding.vm = vm as LikeViewModel
            }
        }

        override fun onBindViewHolder(item: Any?) {
            super.onBindViewHolder(item)
            setCheckBoxState()
        }

        private fun setCheckBoxState() {
            if (binding is RvUserSearchItemBinding) {
                binding.cbStar.isChecked = false
            } else if (binding is RvUserLikeItemBinding) {
                binding.cbStar.isChecked = true
            }
        }
    }
}