package com.example.supplementsonlineshopproject.model.data

data class AddProductToCartResponse(
    val id: Int,
    val product: Int,
    val quantity: Int,
    val error:String
)