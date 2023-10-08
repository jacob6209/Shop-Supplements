package com.example.supplementsonlineshopproject.model.data

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("detail")
    val detail:String,
    @SerializedName("code")
    val code:String,
    @SerializedName("refresh")
    val refresh:String,
    @SerializedName("access")
    val access:String
)
