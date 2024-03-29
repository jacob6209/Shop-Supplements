package com.example.supplementsonlineshopproject.model.data


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("detail")
    val detail:String,
    @SerializedName("refresh")
    val refresh:String,
    @SerializedName("access")
    val access:String

)