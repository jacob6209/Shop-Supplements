package com.example.supplementsonlineshopproject.ui.features.main

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.CheckoutOrder
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.cart.CartRepository
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class MainViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    isInternetConnected:Boolean,
    context: Context
):ViewModel() {

    val dataProducts= mutableStateOf<List<ProductResponse>>(listOf())
    val dataAds= mutableStateOf<List<AdsResponse>>(listOf())
    val showProgressBar= mutableStateOf(false)
    val badgeNumber= mutableStateOf(0)
    val showPaymentDialog= mutableStateOf(false)
    val checkoutData= mutableStateOf(CheckoutOrder(null,null,null, null, null))
    val showMenu= mutableStateOf(false)
    val showSubMenu= mutableStateOf(false)

    private val _selectedLanguage = MutableLiveData<String>("en")
    val selectedLanguage: LiveData<String>
        get() = _selectedLanguage


    fun toggleShowMenu() {
        showMenu.value = !showMenu.value
    }

    fun toggleShowMenuWithDelay() {
        viewModelScope.launch {
            toggleShowMenu()
        }
    }

    fun toggleShowSubMenu() {
        showSubMenu.value = !showSubMenu.value
    }
    fun setSelectedLanguage(language: String, context: Context) {
        _selectedLanguage.value = language
        setLocation(context, language)
    }


     fun setLocation(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale

        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        val editor = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

     fun loadLocation(context: Context) {
        val sharedPreferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang","")
        _selectedLanguage.value = language
        setLocation(context, language!!)
    }


    init {
        refreshAllDataFromNet(isInternetConnected)
        loadLocation(context)
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

