package com.example.supplementsonlineshopproject.ui.features.category

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

class CategoryViewModel(
    private val productRepository: ProductRepository,
):ViewModel() {

    val dataProducts= mutableStateOf<List<ProductResponse>>(listOf())

     fun loadDataByCategory(categoryTitle:String) {

         viewModelScope.launch {
             val dataFromLocal=productRepository.getAllProductByCategory(categoryTitle)
             dataProducts.value=dataFromLocal

         }
     }

}

