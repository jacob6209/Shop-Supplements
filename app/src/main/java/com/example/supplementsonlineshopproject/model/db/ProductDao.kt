package com.example.supplementsonlineshopproject.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.supplementsonlineshopproject.model.data.CategoryResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(product:List<ProductResponse>)

    @Query("SELECT * FROM Product_tabel")
    suspend fun getAll(): List<ProductResponse>

    @Query("SELECT * FROM Product_tabel WHERE id=:productId")
    suspend fun getProductById(productId:Int): ProductResponse
    @Query("SELECT * FROM Product_tabel WHERE category_title=:category")
    suspend fun getAllByCategory(category:String):List<ProductResponse>



}