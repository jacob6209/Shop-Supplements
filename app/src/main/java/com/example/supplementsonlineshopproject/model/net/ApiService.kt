package com.example.supplementsonlineshopproject.model.net

import android.util.Log
import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.LoginResponse
import com.example.supplementsonlineshopproject.model.data.PassResetResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.data.RefreshToken
import com.example.supplementsonlineshopproject.model.data.SignUpResponse
import com.example.supplementsonlineshopproject.model.repository.TokenInMemory
import com.example.supplementsonlineshopproject.util.BASE_URL
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/")
    suspend fun signUp(@Body jsonObject: JsonObject):Response<SignUpResponse>

    @POST("auth/jwt/create/")
   suspend fun signIn(@Body jsonObject: JsonObject):LoginResponse

    @POST("auth/password_reset/?")
    suspend fun PassReset(@Body jsonObject: JsonObject):PassResetResponse

    @POST("auth/jwt/refresh/")
    fun refreshToken(@Body jsonObject: JsonObject):Call<RefreshToken>
    @GET("store/products/")
    suspend fun getAllProducts():Response<List<ProductResponse>>
    @GET("store/getads/")
    suspend fun getAds():Response<List<AdsResponse>>

    @GET("store/products/{productId}")
    suspend fun getSpecificProductWithComments(@Path("productId") productId: Int):ProductResponse

}

fun CreateApiService():ApiService{

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequest = oldRequest.newBuilder()
            // to do=> remove JWT header Type
            if (TokenInMemory.access != null){
//                newRequest.addHeader("Authorization",TokenInMemory.access!!)
                newRequest.addHeader("Authorization", "JWT" + TokenInMemory.access!!)
            }
                newRequest.addHeader("Accept", "application/json")
                newRequest.method(oldRequest.method, oldRequest.body)
            return@addInterceptor it.proceed(newRequest.build())
        }.build()


    val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)


}