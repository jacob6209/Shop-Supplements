package com.example.supplementsonlineshopproject.model.repository

object TokenInMemory {

    var userEmail: String? = null
        private set

    var refreshToken: String? = null
        private set

    var accessToken: String? = null
//        private set

    fun saveOnlyAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }

    fun saveTokens(userEmail: String?,accessToken: String?, refreshToken: String?) {
        this.userEmail = userEmail
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

}