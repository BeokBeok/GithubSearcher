package com.githubsearcher.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.githubsearcher.base.BaseRecyclerView
import com.githubsearcher.data.Users

@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceItems", "page")
fun RecyclerView.replaceItems(items: List<Users>?, page: Int) {
    (this.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
        if (page > 1) appendItems(items) else replaceItems(items)
        notifyDataSetChanged()
    }
}