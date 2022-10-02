package com.izo.newsapp.ui.detail

import androidx.lifecycle.ViewModel
import com.izo.newsapp.data.Repository
import com.izo.newsapp.data.local.NewsEntity

class DetailViewModel(private val repository: Repository) : ViewModel() {

    fun insertData(bookmarkNews: NewsEntity) = repository.insertData(bookmarkNews)

    fun isBookmark(url: String) = repository.isBookmark(url)

    fun deleteData(url: String) = repository.deleteData(url)


}