package com.githubsearcher.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.githubsearcher.base.BaseRecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceItems")
fun RecyclerView.replaceItems(items: List<Any>?) {
    (this.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
        if (items.isNullOrEmpty()) replaceItems(items) else appendItems(items)
        notifyDataSetChanged()
    }
}
