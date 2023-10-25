package com.example.supplementsonlineshopproject.model.net

import com.example.supplementsonlineshopproject.model.data.AddNewCommentResponse
import com.example.supplementsonlineshopproject.model.data.AddProductToCartResponse
import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.CreateCartResponse
import com.example.supplementsonlineshopproject.model.data.LoginResponse
import com.example.supplementsonlineshopproject.model.data.PassResetResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.data.RefreshToken
import com.example.supplementsonlineshopproject.model.data.SignUpResponse
import com.example.supplementsonlineshopproject.model.data.UserCartInfo
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

    @GET("store/products/{productId}/")
    suspend fun getSpecificProductWithComments(@Path("productId") productId: Int):ProductResponse
    @POST("store/products/{productId}/comments/")
    suspend fun addNewcomment(@Path("productId") id: Int,@Body jsonObject: JsonObject):AddNewCommentResponse
    @POST("store/cart/")
    suspend fun CreateUserCart():Response<CreateCartResponse>
    @POST("store/cart/{cartId}/items/")
    suspend fun AddProductToCart(@Path("cartId") id:String,@Body jsonObject: JsonObject):Response<AddProductToCartResponse>

    @GET("store/cart/{cartId}/items/")
    suspend fun getUserCart(@Path("cartId") id:String):Response<List<UserCartInfo>>

}

fun CreateApiService():ApiService{

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequest = oldRequest.newBuilder()
            val pathSegments = oldRequest.url.encodedPathSegments
           if (TokenInMemory.accessToken != null &&  pathSegments.isNotEmpty() && pathSegments.contains("comments")  ) {
                newRequest.addHeader("Authorization", "JWT " + TokenInMemory.accessToken!!)
            }else if(TokenInMemory.accessToken != null){
                newRequest.addHeader("Authorization", "JWT" + TokenInMemory.accessToken!!)
            }
                newRequest.addHeader("Accept", "application/json")
                newRequest.method(oldRequest.method, oldRequest.body)
            return@addInterceptor it.proceed(newRequest.build())
        }
        .authenticator(AuthChecker())
        .build()


    val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)


}