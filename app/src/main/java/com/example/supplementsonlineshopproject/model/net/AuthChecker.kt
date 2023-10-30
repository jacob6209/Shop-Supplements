package com.example.supplementsonlineshopproject.model.net

import android.util.Log
import com.example.supplementsonlineshopproject.model.data.RefreshToken
import com.example.supplementsonlineshopproject.model.repository.TokenInMemory
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AuthChecker : Authenticator, KoinComponent {
    private val apiService: ApiService by inject()

    override fun authenticate(route: Route?, response: Response): Request? {

        if (TokenInMemory.accessToken != null && !response.request.url.pathSegments.last().equals("auth/jwt/refresh/", false)) {

            val result = refreshToken()
            if (result) {
                // Get the new access token from TokenInMemory or wherever you store it after refreshing
                val newAccessToken = TokenInMemory.accessToken
                return response.request.newBuilder()
                    .addHeader("Authorization","JWT $newAccessToken")
                    .build()
            }

        }

        return null
    }

    private fun refreshToken(): Boolean {
        try {
            val jsonObject = JsonObject().apply {
                addProperty("refresh", TokenInMemory.refreshToken)

            }
            val request: retrofit2.Response<RefreshToken> = apiService.refreshToken(jsonObject).execute()
            if (request.body() != null) {
                if (request.body()!!.access.isNotEmpty()) {
                    TokenInMemory.accessToken = request.body()!!.access
                    return true
                }
            }
            return false
        }catch (e:Exception){
            val errorMessage = "Token refresh failed: ${e.message}"
            Log.e("TokenRefresh", errorMessage)
            throw TokenRefreshFailedException("Token refresh failed: ${e.message}")
        }

    }

}
class TokenRefreshFailedException(message: String) : Exception(message)