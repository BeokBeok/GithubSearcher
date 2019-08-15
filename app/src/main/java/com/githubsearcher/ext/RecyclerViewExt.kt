package com.githubsearcher.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.githubsearcher.base.BaseRecyclerView

private const val SEARCH = 0
private const val LIKE = 1

@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceItems", "tabId")
fun RecyclerView.replaceItems(items: List<Any>?, tabId: Int) {
    (this.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
        if (tabId == SEARCH) {
            if (items.isNullOrEmpty()) replaceItems(items) else appendItems(items)
        } else if (tabId == LIKE) {
            replaceItems(items)
        }
        notifyDataSetChanged()
    }
}