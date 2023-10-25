package com.example.supplementsonlineshopproject.model.repository.user

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.supplementsonlineshopproject.model.data.SignUpResponse
import com.example.supplementsonlineshopproject.model.net.ApiService
import com.example.supplementsonlineshopproject.model.repository.TokenInMemory
import com.example.supplementsonlineshopproject.util.MyUtility.showToast
import com.example.supplementsonlineshopproject.util.VALUE_NOT_SUCCESS
import com.example.supplementsonlineshopproject.util.VALUE_SUCCESS
import com.google.gson.JsonObject
import retrofit2.Response
import java.sql.SQLException

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPref: SharedPreferences
) : UserRepository {
    override suspend fun signUp(username: String, email: String, password: String): String {

         try {
            val jsonObject = JsonObject().apply {
                addProperty("username", username)
                addProperty("email", email)
                addProperty("password", password)
            }
            val result: Response<SignUpResponse> = apiService.signUp(jsonObject)
            val statusCode = result.code()
             return when (statusCode) {
                 201 -> result.body()?.success.toString()
                 400 -> result.body()?.error_message?:"User with this email or username already exists."
                 else -> "Unexpected status code: $statusCode"
             }
        }catch (e: Exception) {
            return "An error occurred during the API call: ${e.message}"
        }
    }


    override suspend fun signIn(email: String, password: String, context: Context): String {

        try {
            val jsonObject = JsonObject().apply {
                addProperty("email", email)
                addProperty("password", password)
            }
            val result = apiService.signIn(jsonObject)

            if (result.access.isNotEmpty()) {
                TokenInMemory.saveTokens(email,result.refresh, result.access)
                saveToken(result.access)
                saveRefreshToken(result.refresh)
                saveUserLoginTime()
                saveEmail(email)

                return VALUE_SUCCESS
            } else {
                return result.detail
            }
        }catch (e: Exception) {
//            showToast(context, "Invalid credentials or account not activated. Please check your information or verify your email. ${e.message}")
            return "Invalid credentials or account not activated. ${e.message}"
        }

    }

    override suspend fun resetPass(email: String): String {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
        }
        val result = apiService.PassReset(jsonObject)

        if (result.status=="ok") {
            return VALUE_SUCCESS
        } else {
            return VALUE_NOT_SUCCESS
        }
    }

    override fun signOut() {
        TokenInMemory.saveTokens(null,null,null)
        sharedPref.edit().clear().apply()
    }


//    override fun loadToken() {
//        val token = getToken()
//        if (token != null) {
//            TokenInMemory.refreshToken(token, getRefreshToken())
//            Log.d("TokenLoading", "Token loaded: $token")
//        } else {
//            Log.d("TokenLoading", "Token not found in SharedPreferences")
//        }
//    }



    override fun loadToken() {
        TokenInMemory.saveTokens(getEmail(),getToken(),getRefreshToken())
    }

    override fun saveToken(newToken: String) {
        sharedPref.edit().putString("access", newToken).apply()
    }

    override fun getToken(): String? {
        return sharedPref.getString("access",null)

    }


    override fun saveRefreshToken(refresh: String) {
        sharedPref.edit().putString("refresh", refresh).apply()
    }

    override fun getRefreshToken(): String? {
        return sharedPref.getString("refresh",null)
    }

    override fun saveEmail(email: String) {
        sharedPref.edit().putString("email", email).apply()
    }

    override fun getEmail(): String? {
        return sharedPref.getString("email",null)
    }

    override fun saveUserLocation(address: String, postalCode: String) {
        sharedPref.edit().putString("address",address).apply()
        sharedPref.edit().putString("postalCode",postalCode).apply()
    }

    override fun getUserLocation(): Pair<String, String> {
        val address=sharedPref.getString("address","Click to add")!!
        val postalCode=sharedPref.getString("postalCode","Click to add")!!
        return  Pair(address,postalCode)
    }

    override fun saveUserLoginTime() {
        val now=System.currentTimeMillis()
        sharedPref.edit().putString("login_time",now.toString()).apply()
    }

    override fun getUserLoginTime(): String {
        return sharedPref.getString("login_time","0")!!
    }

}