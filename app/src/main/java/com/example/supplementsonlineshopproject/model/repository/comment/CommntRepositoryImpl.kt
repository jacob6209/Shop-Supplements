package com.example.supplementsonlineshopproject.model.repository.comment

import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.net.ApiService
import com.example.supplementsonlineshopproject.util.CommentException
import com.example.supplementsonlineshopproject.util.EMPTY_PRODUCT
import com.google.gson.JsonObject


class CommntRepositoryImpl(
    private val apiService: ApiService,
) : CommentRepository {
    override suspend fun getAllComments(productId: Int): ProductResponse {
        val data = apiService.getSpecificProductWithComments(productId)
        if (data?.comments?.isNotEmpty() == true) {
            return data
        }
        return EMPTY_PRODUCT
    }

    override suspend fun AddNewCommentResponse(
        id: Int,
        name: String,
        body: String,
        rating: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try{
            val jsonObject = JsonObject().apply {
                addProperty("id", id)
                addProperty("name", name)
                addProperty("body", body)
                addProperty("rating", rating)
            }
            val result = apiService.addNewcomment(id = id, jsonObject)
            if (result.error == null && result.body == body) {
                onSuccess.invoke()
            } else {
                onError.invoke(result.error)

            }
        }catch  (e: Exception){
            throw CommentException("Failed to add comment. Please try again later.")
        }


    }


}