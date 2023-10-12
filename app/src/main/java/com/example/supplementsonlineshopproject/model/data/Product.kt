package com.example.supplementsonlineshopproject.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity("Product_tabel")
data class ProductResponse(
    @PrimaryKey
    val id: Int,
    val average_rating: Double,
    val category: Int,
    @TypeConverters(CommentListConverter::class)
    val comments: List<Comment>,
    val description: String,
    val flash_sales: Boolean,
    @TypeConverters(ImageListConverter::class)
    val images: List<Image>,
    val inventory: Int,
    val price_after_tax: Double,
    val slug: String,
    val title: String,
    val top_deal: Boolean,
    val unit_price: Double,
    val tags: String?,
    val soled_item:Int,

)
data class Image(
    val id: Int,
    val image: String,
    val product: Int,
    @Transient
    val image_url: String // Add the image URL property here
)
data class Comment(
    val body: String,
    val id: Int,
    val name: String,
    val rating: Int
)
//--------------------------------------------------------------------------------------------------
class CommentListConverter {
    @TypeConverter
    fun fromString(value: String): List<Comment> {
        val listType = object : TypeToken<List<Comment>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Comment>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class ImageListConverter {
    @TypeConverter
    fun fromString(value: String): List<Image> {
        val listType = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Image>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
