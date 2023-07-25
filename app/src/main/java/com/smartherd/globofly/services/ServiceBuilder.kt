package com.smartherd.globofly.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    /**
     * this app is using localhost as its server
     * so, in case of running this app on a live server,
     * this URL must be changed to the server URL, such as "https://xyz.com"
     */
    private var BASE_URL  = "http://10.0.2.2:9000/"
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // custom setter for BASE_URL
    fun setBaseURL(url: String) {
        BASE_URL = url
    }

    // Creating OkHttp Client
    private val okHTTP = OkHttpClient.Builder().addInterceptor(logger)

    // Creating Retrofit Builder
    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHTTP.build())

    // creating retrofit instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}