package com.githubsearcher.searchlike

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.githubsearcher.base.BaseRecyclerView
import com.githubsearcher.databinding.RvUserSearchLikeItemBinding

class SearchLikeAdapter<A : Any, VDB : ViewDataBinding>(
    @LayoutRes
    private val layoutRes: Int,
    private val bindingId: Int,
    private val vm: SearchLikeViewModel
) : BaseRecyclerView.Adapter<A, VDB>(
    layoutRes,
    bindingId
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
        bindingId
    ) {

        override fun onBindViewHolder(item: Any?) {
            super.onBindViewHolder(item)
            if (binding is RvUserSearchLikeItemBinding) {
                binding.vm = vm
            }
        }
    }
}