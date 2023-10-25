package com.example.supplementsonlineshopproject.ui.features.profile
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.supplementsonlineshopproject.model.repository.user.UserRepository

class ProfileViewModel(
    private val userRepository:UserRepository
):ViewModel() {
    val email= mutableStateOf("")
    val address= mutableStateOf("")
    val postalcode= mutableStateOf("")
    val loginTime= mutableStateOf("")
    val ShowLocationDialog =  mutableStateOf(false)

    fun loadUserData(){
        email.value=userRepository.getEmail()!!
        loginTime.value=userRepository.getUserLoginTime()
        val location=userRepository.getUserLocation()
        address.value=location.first
        postalcode.value=location.second

    }
    fun signOut(){
        userRepository.signOut()

    }
    fun setUserLocation(address:String,postalCode:String){
        userRepository.saveUserLocation(address,postalCode)
    }




}



