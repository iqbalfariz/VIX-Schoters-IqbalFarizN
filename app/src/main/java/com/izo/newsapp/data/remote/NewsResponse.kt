package com.izo.newsapp.data.remote

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsResponse (
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Articles>
        )


@Parcelize
data class Articles(
    @Expose
    @SerializedName("source")
    val source: Source,
    @Expose
    @SerializedName("author")
    val author: String,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("url")
    val url: String,
    @Expose
    @SerializedName("urlToImage")
    val urlToImage: String,
    @Expose
    @SerializedName("publishedAt")
    val publishedAt: String,
    @Expose
    @SerializedName("content")
    val content: String
) : Parcelable

@Parcelize
data class Source(
    @Expose
    @SerializedName("name")
    val name: String
) : Parcelable