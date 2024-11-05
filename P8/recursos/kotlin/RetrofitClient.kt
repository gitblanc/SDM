package com.example.p3recycler.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_THEMOVIEDB_ORG_3 = "https://api.themoviedb.org/3/"

object RetrofitClient {
    fun getRetrofit(): ThemoviedbApi {
        return Retrofit.Builder()
            .baseUrl(API_THEMOVIEDB_ORG_3)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ThemoviedbApi::class.java)
    }
}