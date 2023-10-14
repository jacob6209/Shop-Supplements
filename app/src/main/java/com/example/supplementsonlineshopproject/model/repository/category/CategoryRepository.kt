package com.example.supplementsonlineshopproject.model.repository.category

interface CategoryRepository {
    suspend fun getCategoryNameById(categoryId: Int):String
}