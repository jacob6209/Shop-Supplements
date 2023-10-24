package com.example.supplementsonlineshopproject.model.data

data class CreateCartResponse(
    val id: String,
    val items: List<Any>,
    val total_price: Int
)