package com.smartherd.globofly.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceBuilderTest {
    private var BASE_URL  = "http://10.0.2.2:9000/"

    private val client = OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <T> buildService(service: Class<T>): T {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logging)
        retrofit.client(client.build())

        return retrofit.build().create(service)
    }
}