package com.izo.newsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(news: NewsEntity)

    @Query("DELETE from news WHERE url = :url")
    fun deleteBookmark(url: String)

    @Query("SELECT * from news ORDER BY newsId ASC")
    fun getAllNewsEntitys(): LiveData<List<NewsEntity>>

    @Query("SELECT EXISTS (SELECT * from news WHERE url = :url)")
    fun isBookmark(url: String): LiveData<Boolean>

}