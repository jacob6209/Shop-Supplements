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
    val userFirst_Name= mutableStateOf("")
    val userLast_Name= mutableStateOf("")
    val userPhone_Number= mutableStateOf("")
    val userProvince= mutableStateOf("")
    val userCity= mutableStateOf("")
    val userStreet= mutableStateOf("")
    val loginTime= mutableStateOf("")
    val ShowLocationDialog =  mutableStateOf(false)

    fun loadUserData(){
//        user info
        email.value=userRepository.getEmail()!!
        loginTime.value=userRepository.getUserLoginTime()
        val location=userRepository.getUserLocation()
        address.value=location.first
        postalcode.value=location.second
        userFirst_Name.value= userRepository.getFirst_Name()!!
        userLast_Name.value=userRepository.getLast_Name()!!
        userPhone_Number.value=userRepository.getPhone_Number()!!
        userProvince.value=userRepository.getProvince()!!
        userCity.value=userRepository.getCity()!!
        userStreet.value=userRepository.getStreet()!!

    }
    fun signOut(){
        userRepository.signOut()

    }
    fun setUserLocation(
        address: String,
        postalCode: String,
        f_name: String,
        l_name: String,
        phone_number: String,
        province: String,
        city: String,
        street: String
    ) {
        userRepository.saveUserLocation(
            address, postalCode, f_name,
            l_name,
            phone_number,
            province,
            city,
            street
        )
    }




}



