package com.goodylabs.android.interview.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.getSystemService
import com.bumptech.glide.Glide
import com.goodylabs.android.interview.R
import java.text.SimpleDateFormat
import java.util.*

fun Activity.hideSoftInput() {
    getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(
        window.decorView.applicationWindowToken,
        0
    )
}

fun View.showSoftInput() {
    context.getSystemService<InputMethodManager>()?.showSoftInput(this, 0)
}

fun ImageView.setImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .placeholder(R.drawable.ic_person)
        .into(this)
}

fun String.formatToNormalDate(): String {
    // should be done with simpleDateFormat, but I'm in a bit of a hurry
    return this.substring(0..9)
}