package com.smartherd.globofly.services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DestinationService {
    @GET ("destination")
    fun getDestinationList(@QueryMap filter: HashMap<String, String>): Call<List<Destination>>

    @GET ("destination/{id}")
    fun getDestination(@Path("id")id: Int): Call<Destination>

    @POST ("destination")
    fun addDestination(@Body newDestination: Destination): Call<Destination>

    @GET ("messages")
    fun getMessages(): Call<String>
}