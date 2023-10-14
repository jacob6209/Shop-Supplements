package com.example.supplementsonlineshopproject.ui.features.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.model.repository.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.math.log

class MainViewModel(
    private val productRepository: ProductRepository,
    isInternetConnected:Boolean
):ViewModel() {

    val dataProducts= mutableStateOf<List<ProductResponse>>(listOf())
    val dataAds= mutableStateOf<List<AdsResponse>>(listOf())
    val showProgressBar= mutableStateOf(false)

    init {
        refreshAllDataFromNet(isInternetConnected)
    }
    private fun refreshAllDataFromNet(isInternetConnected:Boolean){
        viewModelScope.launch {
            if (isInternetConnected)
                showProgressBar.value=true
            val newDataProduct =async{productRepository.getAllProducts(isInternetConnected)}
            val newDataAds =async{productRepository.getAllAds(isInternetConnected)}
            updateData(newDataProduct.await(),newDataAds.await())

            showProgressBar.value=false
        }

    }

    private fun updateData(products:List<ProductResponse>,ads:List<AdsResponse>){
        dataProducts.value =products
        dataAds.value=ads
    }



}
