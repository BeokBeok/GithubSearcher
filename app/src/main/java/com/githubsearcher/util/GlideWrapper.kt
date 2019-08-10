package com.githubsearcher.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.githubsearcher.R

object GlideWrapper {
    fun loadImage(
        context: Context,
        url: String,
        targetView: ImageView
    ) {
        Glide.with(context)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .into(targetView)
    }
}