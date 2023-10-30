package com.example.supplementsonlineshopproject.ui.features.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.data.UserCartInfo
import com.example.supplementsonlineshopproject.model.net.ApiService
import com.example.supplementsonlineshopproject.model.repository.cart.CartInMemory
import com.example.supplementsonlineshopproject.model.repository.cart.CartRepository
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.model.repository.user.UserRepository
import com.example.supplementsonlineshopproject.ui.features.main.MainViewModel
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Response

class CartViewModel(
    val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val productList = mutableStateOf(listOf<UserCartInfo>())
    val productdata = mutableStateOf<List<ProductResponse>>(listOf())
    val totalPrice = mutableStateOf(0)
    val errorMessage = mutableStateOf<String?>(null)
    val isChangingNumber= mutableStateOf(Pair("",false))
//    val order_id= mutableStateOf("")
//    val process_Link= mutableStateOf("")
    private fun calculateTotalPrice(cartItems: List<UserCartInfo>): Int {
        return cartItems.sumBy { it.item_total.toInt() }
    }
    fun getUserLocation():Pair<String,String>{
        return  userRepository.getUserLocation()
    }
    fun setUserLocation(address:String,postalcode:String,f_name:String,l_name:String,phone_number:String,province:String,city:String,street:String){
        userRepository.saveUserLocation(address,postalcode,f_name,l_name,phone_number,province,city,street)

    }
    fun purchaseAll(
        cartId: String,
        address: String,
        postalcode: String,
        f_name:String,
        l_name:String,
        phone:String,
        province:String,
        city:String,
        street:String,
        isSuccess:(Boolean,String)->Unit){
        viewModelScope.launch (coroutinExceptionHandler){
            val result=cartRepository.submitOrder(cartId,address,postalcode,f_name,l_name,phone,province,city,street)
            isSuccess.invoke(result.Success,result.Linke)
        }
    }
//    fun paymentProcess(order_id:String){
//        viewModelScope.launch (coroutinExceptionHandler){
//            val result=cartRepository.paymentProcess(order_id)
//            if (result.isSuccessful){
//                process_Link.value=result.body()?.Linke!!
//            }
//        }
//    }

    fun setPaymentStatus(status:Int){
        cartRepository.setPurchaseStatus(status)
    }

    fun loadCartdata() {
        viewModelScope.launch(coroutinExceptionHandler) {
//            if(cartRepository.loadCartId()!=null){
                try {
                    val response = cartRepository.getCartUserInfo(cartRepository.getCartId())
                    if (response.isSuccessful) {
                        val cartItems = response.body()
                        if (cartItems != null) {
                            productdata.value=productRepository.getAllProducts(true)
                            productList.value = cartItems // Update productList with the response
                            totalPrice.value = calculateTotalPrice(cartItems) // Calculate total price and update totalPrice
                        } else {
                                errorMessage.value = "Cart is empty."
                        }
                    } else if (response.isSuccessful){
                        errorMessage.value = "Error loading cart data: ${response.message()}"
                    }
                } catch (e: Exception) {
                    errorMessage.value = "Error occurred while loading cart data: ${e.message}"
                }
//            }else{
//                errorMessage.value = "Cart is empty.Please add some item first"
//            }
        }
    }

    fun addItem(cartId:String,productId:String){
        viewModelScope.launch {
            isChangingNumber.value=isChangingNumber.value.copy(productId,true)
            val result=cartRepository.addToCart(cartId,productId)
            if (result.isSuccessful){
                loadCartdata()
            }
            delay(100)
            isChangingNumber.value=isChangingNumber.value.copy(productId,false)
        }
    }
    fun removeItem(cartId:String,productId:String){
        viewModelScope.launch {
            isChangingNumber.value=isChangingNumber.value.copy(productId,true)
            val result=cartRepository.removeFromCart(cartId,productId)
            if (result.isSuccessful){
                loadCartdata()
            }
            delay(100)
            isChangingNumber.value=isChangingNumber.value.copy(productId,false)
        }


    }

    fun getFirstName():String{
        return  userRepository.getFirst_Name()!!
    }
    fun setFirstName(f_name: String){
        userRepository.SaveFirst_Name(f_name)
    }

    fun getLastName():String{
        return  userRepository.getLast_Name()!!
    }
    fun setLastName(l_name: String){
        userRepository.SaveLast_Name(l_name)
    }

    fun getPhone_number():String{
        return  userRepository.getPhone_Number()!!
    }
    fun setPhone_number(Phone_number: String){
        userRepository.SavePhone_Number(Phone_number)
    }
    fun getProvince():String{
        return  userRepository.getProvince()!!
    }
    fun setProvince(province: String){
        userRepository.SaveProvince(province)
    }
    fun getCity():String{
        return  userRepository.getCity()!!
    }
    fun setCity(city: String){
        userRepository.SaveCity(city)
    }

    fun getStreet():String{
        return  userRepository.getStreet()!!
    }
    fun setStreet(street: String){
        userRepository.SaveStreet(street)
    }





}



