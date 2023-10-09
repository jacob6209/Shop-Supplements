package com.example.supplementsonlineshopproject.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.supplementsonlineshopproject.model.data.CommentListConverter
import com.example.supplementsonlineshopproject.model.data.ImageListConverter
import com.example.supplementsonlineshopproject.model.data.ProductResponse

@Database(entities = [ProductResponse::class], version = 1, exportSchema = false)
@TypeConverters(CommentListConverter::class, ImageListConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun productDao():ProductDao
}