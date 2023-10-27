package com.example.supplementsonlineshopproject.model.data

data class UserCartInfo(

    val id: Int,
    val item_total: Double,
    val product: Product,
    val quantity: Int?
)
data class Product(
    val id: Int,
    val name: String,
    val unit_price: Double
)