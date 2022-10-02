package com.izo.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): NewsDatabase {
            if (INSTANCE == null) {
                synchronized(NewsDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java, "News.db"
                    )
                        .build()
                }
            }
            return INSTANCE as NewsDatabase
        }
    }
}