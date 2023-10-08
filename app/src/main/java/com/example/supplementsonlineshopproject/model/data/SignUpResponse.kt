package com.example.supplementsonlineshopproject.model.data


import com.google.gson.annotations.SerializedName




data class SignUpResponse(
        @SerializedName("email")
        val email: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("success")
        val success:Boolean,
        @SerializedName("error_message")
        val error_message:String,
        @SerializedName("message")
        val message:String,
        @SerializedName("password")
        val password:String
    )
