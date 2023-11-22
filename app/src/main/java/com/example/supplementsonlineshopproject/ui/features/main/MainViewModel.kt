package com.example.supplementsonlineshopproject.ui.features.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.CheckoutOrder
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.cart.CartRepository
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    isInternetConnected:Boolean,
):ViewModel() {

    val dataProducts= mutableStateOf<List<ProductResponse>>(listOf())
    val dataAds= mutableStateOf<List<AdsResponse>>(listOf())
    val showProgressBar= mutableStateOf(false)
    val badgeNumber= mutableStateOf(0)

    val showPaymentDialog= mutableStateOf(false)
    val checkoutData= mutableStateOf(CheckoutOrder(null,null,null, null, null))

    init {
        refreshAllDataFromNet(isInternetConnected)
    }

    fun getCheckoutData(){
        viewModelScope.launch(coroutinExceptionHandler) {

            val result=cartRepository.checkOut(cartRepository.getOrderId())
            if (result.isSuccessful){
                checkoutData.value=result.body()!!
                showPaymentDialog.value=true

                
            }

        }

    }
    fun getPaymentStatus():String?{
        return cartRepository.getPurchaseStatus()
    }
    fun setPaymentStatus(status:String){
        cartRepository.setPurchaseStatus(status)
    }


    private fun refreshAllDataFromNet(isInternetConnected:Boolean){
        viewModelScope.launch(coroutinExceptionHandler){
            if (isInternetConnected)
                showProgressBar.value=true
            val newDataProduct =async{productRepository.getAllProducts(isInternetConnected)}
            val newDataAds =async{productRepository.getAllAds(isInternetConnected)}
            updateData(newDataProduct.await(),newDataAds.await())
            loadBadgeNumber()
            showProgressBar.value=false
        }

    }

    private fun updateData(products:List<ProductResponse>,ads:List<AdsResponse>){
        dataProducts.value =products
        dataAds.value=ads
    }

     fun loadBadgeNumber(){
        viewModelScope.launch(coroutinExceptionHandler) {
            badgeNumber.value=cartRepository.getCartSize(cartRepository.getCartId()!!)
//            badgeNumber.value=cartRepository.getCartSize(CartInMemory.cartId!!)
        }
    }



}

