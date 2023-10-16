package com.example.supplementsonlineshopproject.model.repository.comment

import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.net.ApiService
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository


class CommntRepositoryImpl(
    private val apiService: ApiService,
) : CommentRepository {
    override suspend fun getAllComments(productId: Int): ProductResponse {
        val data = apiService.getSpecificProductWithComments(productId)
        if (data!!.comments.isNotEmpty()) {
            return data
        }
        throw NoSuchElementException("No comments found for productId: $productId")
    }
}