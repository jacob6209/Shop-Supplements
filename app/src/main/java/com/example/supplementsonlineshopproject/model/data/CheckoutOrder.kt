package com.example.supplementsonlineshopproject.model.data

data class CheckoutOrder(
    val customer: Customer?,
    val datetime_created: String?,
    val id: Int?,
    val items: List<Item>?,
    val status: String?
)
data class Customer(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String
)
data class Item(
    val id: Int,
    val product: Productxy,
    val quantity: Int,
    val unit_price: Double
)
data class Productxy(
    val id: Int,
    val name: String,
    val unit_price: Double
)