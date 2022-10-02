package com.izo.newsapp.ui.main

import androidx.lifecycle.ViewModel
import com.izo.newsapp.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getNews(country: String) = repository.getNews(country)

    fun getNewsRoom() = repository.getData()

}