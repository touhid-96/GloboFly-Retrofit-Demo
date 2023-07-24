package com.smartherd.globofly.services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.GET

interface DestinationService {
//    @GET ("destination")
    @GET("b/4AGW")
    fun getDestinationList(): Call<List<Destination>>
}