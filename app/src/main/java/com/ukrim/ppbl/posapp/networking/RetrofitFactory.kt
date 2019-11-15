package com.ukrim.ppbl.posapp.networking

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ukrim.ppbl.posapp.util.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitFactory {
    val host = "103.28.112.108"
    val BASE_URL = "http://$host/webservice/index.php/api/v1/"
    fun create(): RetrofitService {
        var builder = OkHttpClient().newBuilder()
        builder.connectTimeout(15, TimeUnit.SECONDS)

        var logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY

        val retrofit = Retrofit.Builder()
            .client(builder.build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(RetrofitService::class.java)
    }

    fun create(context: Context): RetrofitService {
        val token = Preferences.getToken(context)
        var builder = OkHttpClient().newBuilder()
        builder.connectTimeout(15, TimeUnit.SECONDS)

        var logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY
        //add token
        builder.addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("token", token).build() //penyisipan token secara otomatis
            chain.proceed(request)
        }
        val retrofit = Retrofit.Builder()
            .client(builder.build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(RetrofitService::class.java)
    }
}