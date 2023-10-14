package com.example.supplementsonlineshopproject.ui.features.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.repository.user.UserRepository
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.launch
import kotlin.math.log

class SignUpViewModel(private val userRepository: UserRepository):ViewModel() {
    val username=MutableLiveData("")
    val email=MutableLiveData("")
    val password=MutableLiveData("")
    val confirmPassword=MutableLiveData("")



    fun signUpUser(LoggingEvent:(String)->Unit){
  viewModelScope.launch(coroutinExceptionHandler){
      val result= userRepository.signUp(username.value!!,email.value!!,password.value!!)
      LoggingEvent(result)

  }


    }


}

