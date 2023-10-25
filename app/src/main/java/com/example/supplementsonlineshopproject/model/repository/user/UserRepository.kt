package com.example.supplementsonlineshopproject.model.repository.user

import android.content.Context

interface UserRepository {

//  online
   suspend fun signUp(username:String,email:String,password:String):String
   suspend fun signIn(email: String, password: String, context: Context):String
   suspend fun resetPass(username:String):String

//   offline
    fun signOut()
    fun loadToken()

    fun saveToken(newToken:String)
    fun getToken():String?
    fun saveRefreshToken(refresh: String)
    fun getRefreshToken():String?

    fun saveEmail(email: String)
    fun getEmail():String?


    fun saveUserLocation(address:String,postalCode:String)

    fun getUserLocation():Pair<String,String>

    fun saveUserLoginTime()
    fun getUserLoginTime():String

}