package com.example.supplementsonlineshopproject.ui.features.signIn

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.repository.user.UserRepository
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository):ViewModel() {
    val email=MutableLiveData("")
    val password=MutableLiveData("")



    fun signInUser( context: Context,LoggingEvent:(String)->Unit){
        viewModelScope.launch(coroutinExceptionHandler){
            val result= userRepository.signIn(email.value!!,password.value!!,context)
            LoggingEvent(result)

        }

    }


}

