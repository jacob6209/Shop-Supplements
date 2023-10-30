package com.example.supplementsonlineshopproject.model.repository.cart

import android.content.SharedPreferences
import com.example.supplementsonlineshopproject.model.data.AddProductToCartResponse
import com.example.supplementsonlineshopproject.model.data.SubmitOrder
import com.example.supplementsonlineshopproject.model.data.PaymentCallBackResponse
import com.example.supplementsonlineshopproject.model.data.UserCartInfo
import com.example.supplementsonlineshopproject.model.net.ApiService
import com.example.supplementsonlineshopproject.util.NO_PAYMENT
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.Exception

class CartRepositoryImpl(
    private val apiService: ApiService,
    private val sharepref:SharedPreferences
) : CartRepository {
    override suspend fun addToCart(cartId: String,productId: String): Response<AddProductToCartResponse> {

        val jsonObject=JsonObject().apply {
            addProperty("product",productId)
        }
        return try {
            val response = apiService.AddProductToCart(cartId, jsonObject)
            response
        } catch (e: Exception) {
            // Handle the exception and return an error response
            Response.error(500, ResponseBody.create(null, "An error occurred"))
        }

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

    override suspend fun getCartUserInfo(cartId: String?): Response<List<UserCartInfo>> {
        return try {
            val response=apiService.getUserCart(cartId!!)
            response
        } catch (e: Exception) {
            // Handle the exception and return an error response
            Response.error(500, ResponseBody.create(null, "An error occurred"))
        }
    }

    override suspend fun removeFromCart(cartId: String,productId: String):Response<UserCartInfo> {
//        val jsonObject=JsonObject().apply {
//            addProperty("product",productId)
//        }
        return try {
            val response = apiService.removeFromCart(cartId, productId)
            response
        } catch (e: Exception) {
            // Handle the exception and return an error response
            Response.error(500, ResponseBody.create(null, "An error occurred"))
        }
    }


    override suspend fun getCartSize(cartId: String): Int {
        val response=apiService.getUserCart(cartId)
        var counter=0
        if (response.isSuccessful){
            response.body()!!.forEach(){
            counter+=it.quantity?:0
            }
            return counter
        }
        return 0
    }
    override suspend fun submitOrder(
        cart_id:String,
        address: String,
        postalcode: String,
        f_name: String,
        l_name: String,
        ph_number: String,
        province: String,
        city: String,
        steet: String
    ):SubmitOrder {
       val jsonObject=JsonObject().apply {
           addProperty("cart_id",getCartId())
           addProperty("address",address)
           addProperty("zip_code",postalcode)
           addProperty("first_name",f_name)
           addProperty("last_name",l_name)
           addProperty("phone_number",ph_number)
           addProperty("province",province)
           addProperty("city",city)
           addProperty("street",steet)
       }
        val result=apiService.submitOrder(jsonObject)
        setOrderId(result.order_id.toString())
        return result
    }

    override suspend fun paymentProcess(order_id: String): Response<PaymentCallBackResponse> {
        return apiService.PaymentProcess(order_id)
    }

    override  fun setOrderId(order_id: String) {
       sharepref.edit().putString("order_id",order_id).apply()
    }

    override  fun getOrderId(): String {
        return sharepref.getString("order_id","0")!!
    }

    override  fun setPurchaseStatus(status: Int) {
        sharepref.edit().putInt("purchase_status",status).apply()
    }

    override  fun getPurchaseStatus(): Int {
       return sharepref.getInt("purchase_status", NO_PAYMENT)
    }

}