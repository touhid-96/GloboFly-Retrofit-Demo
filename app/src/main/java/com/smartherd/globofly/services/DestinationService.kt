package com.smartherd.globofly.services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @FormUrlEncoded  //different type of request other than JSON
    @PUT ("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("country") country: String,
        @Field("description") description: String
    ): Call<Destination>
}