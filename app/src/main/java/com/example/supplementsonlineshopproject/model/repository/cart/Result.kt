package com.example.supplementsonlineshopproject.model.repository.cart

sealed class Result<out T> {
    data class Success(val data: Boolean) : Result<Boolean>()
    data class Error(val errorMessage: String) : Result<Nothing>()
}