package com.example.supplementsonlineshopproject.ui.features.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.log

class SignUpViewModel:ViewModel() {
    val name=MutableLiveData("")
    val email=MutableLiveData("")
    val password=MutableLiveData("")
    val confirmPassword=MutableLiveData("")



    fun signUpUser(){

    Log.v("test","data id -> "+ name.value)

    }


}

