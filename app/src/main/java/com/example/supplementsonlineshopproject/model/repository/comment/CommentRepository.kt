package com.example.supplementsonlineshopproject.model.repository.comment

import com.example.supplementsonlineshopproject.model.data.AddNewCommentResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository


interface CommentRepository {

    suspend fun getAllComments(productId: Int): ProductResponse
    suspend fun AddNewCommentResponse(
        id: Int, name: String, body: String, rating: Int, onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

}