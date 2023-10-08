package com.example.supplementsonlineshopproject.model.net

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

        if (TokenInMemory.access != null && !response.request.url.pathSegments.last().equals("jwt/refresh/", false)) {

            val result = refreshToken()
            if (result) {
                // Get the new access token from TokenInMemory or wherever you store it after refreshing
                val newAccessToken = TokenInMemory.access
                return response.request.newBuilder()
                    .addHeader("Authorization","JWT $newAccessToken")
                    .build()
            }

        }

        return null
    }

        private fun refreshToken(): Boolean {
            val jsonObject = JsonObject().apply {
                addProperty("refresh", TokenInMemory.refresh)

            }
            val request: retrofit2.Response<RefreshToken> = apiService.refreshToken(jsonObject).execute()
            if (request.body() != null) {
                if (request.body()!!.access.isNotEmpty()) {
                    TokenInMemory.access = request.body()!!.access
                    return true
                }
            }

            return false
        }

    }