package com.izo.newsapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.izo.newsapp.data.local.NewsDao
import com.izo.newsapp.data.local.NewsEntity
import com.izo.newsapp.data.remote.ApiConfig
import com.izo.newsapp.data.remote.ApiService
import com.izo.newsapp.data.remote.Articles
import com.izo.newsapp.data.remote.NewsResponse
import com.izo.newsapp.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) {

    // get news from api
    fun getNews(country: String): LiveData<Result<List<Articles>>> {
        val result = MutableLiveData<Result<List<Articles>>>()
        val client = ApiConfig.getApiService().getListNews(country, Constants.API_KEY)
        result.value = Result.Loading
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        result.value = com.izo.newsapp.data.Result.Success(responseBody.articles)
                    } else {
                        result.value =
                            com.izo.newsapp.data.Result.Error(response.message().toString())
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                result.value = com.izo.newsapp.data.Result.Error(t.message.toString())
            }

        })
        return result
    }

    // input data news ke bookmark
    fun insertData(bookmarkNews: NewsEntity) {
        appExecutors.diskIO.execute {
            newsDao.insertBookmark(bookmarkNews)
        }
    }

    // ambil data bookmark dari room
    fun getData(): LiveData<List<NewsEntity>> = newsDao.getAllNewsEntitys()

    // Delete data news dari room
    fun deleteData(url: String) {
        appExecutors.diskIO.execute {
            newsDao.deleteBookmark(url)
        }
    }

    // Check ada di favorite atau tidak
    fun isBookmark(url: String): LiveData<Boolean> = newsDao.isBookmark(url)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}
