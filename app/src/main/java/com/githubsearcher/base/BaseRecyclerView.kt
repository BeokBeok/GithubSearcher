package com.githubsearcher.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerView {

    open class ViewHolder<VDB : ViewDataBinding>(
        @LayoutRes
        private val layoutRes: Int,
        parent: ViewGroup,
        private val bindingId: Int?
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(
                layoutRes,
                parent,
                false
            )
    ) {

        protected val binding: VDB = DataBindingUtil.bind(itemView)!!

        open fun onBindViewHolder(item: Any?) {
            bindingId?.let {
                binding.setVariable(
                    it,
                    item
                )
            }
        }
    }

    open class Adapter<A : Any, VDB : ViewDataBinding>(
        @LayoutRes
        private val layoutRes: Int,
        private val bindingId: Int?
    ) : RecyclerView.Adapter<ViewHolder<VDB>>() {

        private val items = mutableListOf<A>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder<VDB> = object : ViewHolder<VDB>(
            layoutRes,
            parent,
            bindingId
        ) {}

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(
            holder: ViewHolder<VDB>,
            position: Int
        ) = holder.onBindViewHolder(items[position])

        fun replaceItems(items: List<A>?) {
            items?.let {
                with(this.items) {
                    clear()
                    addAll(it)
                }
            }
        }
    }
}