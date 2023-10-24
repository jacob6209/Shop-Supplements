package com.example.supplementsonlineshopproject.model.repository.cart

import com.example.supplementsonlineshopproject.model.data.CreateCartResponse
import retrofit2.Response


interface CartRepository {

suspend fun addToCart(cartId: String,productId:Int): Boolean
suspend fun createCart(): String?

//offline
fun loadCartId()

fun saveCartId(cartId:String?)
fun getCartId():String?


}