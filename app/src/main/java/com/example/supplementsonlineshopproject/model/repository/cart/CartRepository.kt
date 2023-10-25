package com.example.supplementsonlineshopproject.model.repository.cart

import com.example.supplementsonlineshopproject.model.data.AddProductToCartResponse
import com.example.supplementsonlineshopproject.model.data.CreateCartResponse
import com.example.supplementsonlineshopproject.model.data.UserCartInfo
import retrofit2.Response


interface CartRepository {

suspend fun addToCart(cartId: String,productId:Int): Response<AddProductToCartResponse>
suspend fun createCart(): String?

//offline
fun loadCartId()

fun saveCartId(cartId:String?)
fun getCartId():String?

suspend fun getCartUserInfo(cartId: String):Response<List<UserCartInfo>>
suspend fun getCartSize(cartId: String):Int

}