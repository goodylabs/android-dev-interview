package com.goodylabs.android.interview.util

import android.content.Context
import android.graphics.Color
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.data.models.Status

fun Status.getStatusColor(
    context: Context
): Int {
    return when (this) {
        Status.ALIVE -> context.getColor(R.color.green)
        Status.DEAD -> Color.RED
        Status.UNKNOWN -> Color.GRAY
    }
}

fun Status.getStatusText(
    context: Context
) = when (this) {
    Status.ALIVE -> context.getString(R.string.status_alive)
    Status.DEAD -> context.getString(R.string.status_dead)
    Status.UNKNOWN -> context.getString(R.string.status_unknown)
}
