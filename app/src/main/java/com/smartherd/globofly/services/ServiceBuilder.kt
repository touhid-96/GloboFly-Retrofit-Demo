package com.smartherd.globofly.services

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

object ServiceBuilder {
    /**
     * this app is using localhost as its server
     * so, in case of running this app on a live server,
     * this URL must be changed to the server URL, such as "https://xyz.com"
     */
    private var BASE_URL  = "http://10.0.2.2:9000/"
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    /**
     * Custom Interceptor
     * to apply Headers application-wide
     */
    private val headerInterceptor = Interceptor { chain ->
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("x-device-type", Build.DEVICE)
            .addHeader("Accept-Language", Locale.getDefault().language)
            .build()
        chain.proceed(request)  //resume the request to the server and return the response
    }

    /**
     * custom setter for BASE_URL
     */
    fun setBaseURL(url: String) {
        BASE_URL = url
    }

    // Creating OkHttp Client
    private val okHTTP = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(logger)

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