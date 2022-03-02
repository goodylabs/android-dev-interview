package com.goodylabs.android.interview.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("android:isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.also {
        Picasso.get().load(it).into(imageView)
    }
}

@BindingAdapter("android:textFromRes")
fun textFromRes(textView: TextView, @StringRes resId: Int?) {
    resId?.let { textView.setText(resId) }
}