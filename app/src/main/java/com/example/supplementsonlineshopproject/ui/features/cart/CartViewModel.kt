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
import com.example.supplementsonlineshopproject.ui.features.main.MainViewModel
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Response

class CartViewModel(
    val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    val productList = mutableStateOf(listOf<UserCartInfo>())
    val productdata = mutableStateOf<List<ProductResponse>>(listOf())
    val totalPrice = mutableStateOf(0)
    val errorMessage = mutableStateOf<String?>(null)
    val isChangingNumber= mutableStateOf(Pair("",false))
    private fun calculateTotalPrice(cartItems: List<UserCartInfo>): Int {
        return cartItems.sumBy { it.item_total.toInt() }
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

}



