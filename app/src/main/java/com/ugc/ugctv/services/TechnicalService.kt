package com.ugc.ugctv.services

import android.content.Context
import androidx.annotation.WorkerThread
import com.ugc.ugctv.core.AbstractService
import com.ugc.ugctv.core.RequestCallBack
import com.ugc.ugctv.model.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TechnicalService(context: Context) : AbstractService<Config>(context) {

    @WorkerThread
    fun getConfig(callback: RequestCallBack<Config>) {
        CoroutineScope(Dispatchers.IO).launch {
            execute(
                apiEndPoint.getConfig(),
                callback
            )
        }
    }
}