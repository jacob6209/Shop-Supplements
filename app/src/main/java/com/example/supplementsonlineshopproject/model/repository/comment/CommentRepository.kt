package com.example.supplementsonlineshopproject.model.repository.comment

import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository


interface CommentRepository {

    suspend fun getAllComments(productId:Int):ProductResponse

}