package com.example.supplementsonlineshopproject.model.repository

object TokenInMemory {

    var email: String? = null
        private set


    var refresh:String?=null
        private set
    var access:String?=null

    fun refreshToken(refresh:String?,access:String?) {
        this.refresh=refresh
        this.access=access
    }

}