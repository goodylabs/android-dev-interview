package com.goodylabs.android.interview.data.models

import androidx.annotation.StringRes

sealed class RequestState<T> {
    class Success<T>(val data: T): RequestState<T>()
    class Error<T>(@StringRes val errorMsgRes: Int): RequestState<T>()
    class Loading<T>: RequestState<T>()
}
