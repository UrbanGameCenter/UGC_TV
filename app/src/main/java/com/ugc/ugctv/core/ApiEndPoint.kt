package com.ugc.ugctv.core

import com.ugc.ugctv.model.Config
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {

    companion object {
        const val API = "/api"
        const val V1 = "/v1"

        const val TECHNICAL = "/technical"

    }

    @GET(API + V1 + TECHNICAL + "/config")
    fun getConfig() : Call<Config>

}