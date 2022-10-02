package com.izo.newsapp.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getListNews(
        @Query("country") country: String?, @Query("apiKey") apiKey: String?
    ): Call<NewsResponse>

}