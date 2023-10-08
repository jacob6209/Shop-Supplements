package com.example.supplementsonlineshopproject.model.net

import android.util.Log
import com.example.supplementsonlineshopproject.model.data.LoginResponse
import com.example.supplementsonlineshopproject.model.data.PassResetResponse
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
import retrofit2.http.POST

interface ApiService {
    @POST("users/")
    suspend fun signUp(@Body jsonObject: JsonObject):Response<SignUpResponse>

    @POST("jwt/create/")
   suspend fun signIn(@Body jsonObject: JsonObject):LoginResponse

    @POST("password_reset/?")
    suspend fun PassReset(@Body jsonObject: JsonObject):PassResetResponse

    @POST("jwt/refresh/")
    fun refreshToken(@Body jsonObject: JsonObject):Call<RefreshToken>

}

fun CreateApiService():ApiService{

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequest = oldRequest.newBuilder()

            if (TokenInMemory.access != null){
                newRequest.addHeader("Authorization", "JWT " + TokenInMemory.access!!)
            }
                 Log.d("Request Headers", newRequest.build().headers.toString())
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