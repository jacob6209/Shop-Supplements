package com.example.supplementsonlineshopproject.model.repository.cart

import com.example.supplementsonlineshopproject.model.data.AddProductToCartResponse
import com.example.supplementsonlineshopproject.model.data.SubmitOrder
import com.example.supplementsonlineshopproject.model.data.PaymentCallBackResponse
import com.example.supplementsonlineshopproject.model.data.UserCartInfo
import retrofit2.Response


interface CartRepository {

    suspend fun createCart(): String?

    //offline
    fun loadCartId()

    fun saveCartId(cartId: String?)
    fun getCartId(): String?

    suspend fun getCartSize(cartId: String): Int
    suspend fun getCartUserInfo(cartId: String?): Response<List<UserCartInfo>>

    suspend fun addToCart(cartId: String, productId: String): Response<AddProductToCartResponse>
    suspend fun removeFromCart(cartId: String, productId: String): Response<UserCartInfo>

    suspend fun submitOrder(cart_id:String,address: String,postalcode: String,f_name: String,l_name: String,
             ph_number: String,province: String,city: String,steet: String):SubmitOrder

    suspend fun paymentProcess(order_id:String):Response<PaymentCallBackResponse>

     fun setOrderId(order_id:String)
     fun getOrderId():String

     fun setPurchaseStatus(status:Int)
     fun getPurchaseStatus():Int



}