package com.ugc.ugctv.core

import androidx.annotation.MainThread
import com.ugc.ugctv.model.UgcError

interface RequestCallBack<Result> {

    @MainThread
    fun onSuccess(result : Result)

    @MainThread
    fun onError(error : UgcError)

}