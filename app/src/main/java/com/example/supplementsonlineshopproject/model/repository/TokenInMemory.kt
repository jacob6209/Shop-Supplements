package com.example.supplementsonlineshopproject.model.repository

object TokenInMemory {

    var userEmail: String? = null
        private set

    var refreshToken: String? = null
        private set

    var accessToken: String? = null

    fun saveTokens(userEmail: String?,accessToken: String?, refreshToken: String?) {
        this.userEmail = userEmail
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

}