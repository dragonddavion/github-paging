package com.davion.github.paging.data

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.davion.github.paging.R

@BindingAdapter("avatarUrl")
fun bindAvatar(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation))
            .into(imgView)
    }
}

@BindingAdapter("bindId")
fun bindId(textView: TextView, id: Int) {
    textView.text = id.toString()
}