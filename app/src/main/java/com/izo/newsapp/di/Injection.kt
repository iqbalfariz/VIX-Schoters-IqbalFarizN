package com.izo.newsapp.di

import android.content.Context
import com.izo.newsapp.data.Repository
import com.izo.newsapp.data.local.NewsDatabase
import com.izo.newsapp.data.remote.ApiConfig
import com.izo.newsapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return Repository.getInstance(apiService, dao, appExecutors)

    }
}