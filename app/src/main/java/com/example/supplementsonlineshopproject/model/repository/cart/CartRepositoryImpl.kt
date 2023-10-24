package com.example.supplementsonlineshopproject.model.repository.cart

import android.content.SharedPreferences
import com.example.supplementsonlineshopproject.model.data.AddProductToCartResponse
import com.example.supplementsonlineshopproject.model.data.CreateCartResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.net.ApiService
import com.example.supplementsonlineshopproject.util.CommentException
import com.example.supplementsonlineshopproject.util.EMPTY_PRODUCT
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.Path
import java.lang.Exception

class CartRepositoryImpl(
    private val apiService: ApiService,
    private val sharepref:SharedPreferences
) : CartRepository {
    override suspend fun addToCart(cartId: String,productId: Int): Boolean {
        val jsonObject=JsonObject().apply {
            addProperty("product",productId)
        }
        val result=apiService.AddProductToCart(id = cartId,jsonObject)
        return result.isSuccessful

    }

    override suspend fun createCart(): String? {
//        return try {
        val response = apiService.CreateUserCart()
        if (response.isSuccessful) {
            val cartId = response.body()?.id
            Response.success(cartId)
            return cartId

        } else {
            return null
            // Handle the error based on the response status code
//            Response.error(response.code(), response.errorBody()!!)
        }

        }



    override fun loadCartId(){
      CartInMemory.saveCartId(getCartId())
    }

    override fun saveCartId(cartId:String?) {
        sharepref.edit().putString("cartId",cartId).apply()
    }

    override fun getCartId(): String? {
        return sharepref.getString("cartId",null)
    }


}