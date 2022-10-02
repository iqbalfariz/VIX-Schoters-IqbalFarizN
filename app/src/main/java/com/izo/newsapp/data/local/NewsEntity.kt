package com.izo.newsapp.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "news")
@Parcelize
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "newsId")
    val newsId: Int? = 0,

    @field:ColumnInfo(name = "sourceName")
    val sourceName: String,

    @field:ColumnInfo(name = "author")
    val author: String,

    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "url")
    val url: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @field:ColumnInfo(name = "content")
    val content: String

) : Parcelable