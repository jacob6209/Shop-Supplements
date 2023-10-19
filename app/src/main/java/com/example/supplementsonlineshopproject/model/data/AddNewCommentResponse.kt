package com.example.supplementsonlineshopproject.model.data

data class AddNewCommentResponse(
    val body: String,
    val name:String,
    val id: Int,
    val rating: Int,
    val error:String,
//----------------------
    val code: String,
    val detail: String,
    val messages: List<Message>

)

data class Message(
    val message: String,
    val token_class: String,
    val token_type: String
)
