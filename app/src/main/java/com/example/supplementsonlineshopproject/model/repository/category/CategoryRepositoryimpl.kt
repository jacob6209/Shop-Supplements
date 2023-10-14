package com.example.supplementsonlineshopproject.model.repository.category

import com.example.supplementsonlineshopproject.model.db.ProductDao

class CategoryRepositoryimpl(private val productDao: ProductDao):CategoryRepository{
    override suspend fun getCategoryNameById(categoryId: Int): String {
    return ""
    }

}