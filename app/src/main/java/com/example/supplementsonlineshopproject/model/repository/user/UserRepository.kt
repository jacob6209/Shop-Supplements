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


    fun saveUserLocation(
        address: String,
        postalCode: String,
        f_name: String,
        l_name: String,
        phone_number: String,
        province: String,
        city: String,
        street: String
    )

    fun getUserLocation():Pair<String,String>

    fun saveUserLoginTime()
    fun getUserLoginTime():String

// user info
    fun getFirst_Name():String?
    fun SaveFirst_Name(f_Name: String)

    fun getLast_Name():String?
    fun SaveLast_Name(l_Name: String)
    fun getPhone_Number():String?
    fun SavePhone_Number(phone_Number: String)

    fun getProvince():String?
    fun SaveProvince(province: String)

    fun getCity():String?
    fun SaveCity(city: String)

    fun getStreet():String?
    fun SaveStreet(street: String)



}