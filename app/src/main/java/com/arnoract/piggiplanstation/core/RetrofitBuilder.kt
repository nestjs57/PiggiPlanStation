package com.arnoract.piggiplanstation.core

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(
    private val client: OkHttpClient,
    private val gson: Gson
) {
    fun build(): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://firebasestorage.googleapis.com/v0/b/piggiplan.appspot.com/o/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
