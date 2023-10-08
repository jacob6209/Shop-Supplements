package com.example.supplementsonlineshopproject.model.data


import com.google.gson.annotations.SerializedName

data class PassResetResponse(
    @SerializedName("email")
    val email: List<String>,
    @SerializedName("status")
    val status: String
)