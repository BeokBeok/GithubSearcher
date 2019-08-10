package com.githubsearcher.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.githubsearcher.util.GlideWrapper

@BindingAdapter("srcWithGlide")
fun ImageView.srcWithGlide(url: String) {
    GlideWrapper.loadImage(
        this.context,
        url,
        this
    )
}