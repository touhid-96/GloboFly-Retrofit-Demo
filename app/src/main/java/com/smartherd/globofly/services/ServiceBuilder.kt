package com.smartherd.globofly.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ServiceBuilder {
    //    private var BASE_URL = "http://192.168.50.141:9000/"
    private var BASE_URL = "https://jsonkeeper.com/"

    // custom setter for BASE_URL
    fun setBaseURL(url: String) {
        BASE_URL = url
    }

    // Creating OkHttp Client
    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Create a trust manager that does not validate certificate chains
        val trustAllCertificates: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
                    }

                    override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertificates, java.security.SecureRandom())

        OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .addInterceptor(loggingInterceptor)
                .build()
    }

    // Creating Retrofit Builder
    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)

    // creating retrofit instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
