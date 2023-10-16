package com.example.supplementsonlineshopproject.ui.features.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.comment.CommentRepository
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.util.EMPTY_PRODUCT
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.launch



class ProductViewModel(

    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository

):ViewModel() {

    fun loadData(productId: Int,isInternetConnected:Boolean){
        loadProductFromCash(productId)
        if(isInternetConnected){
            loadAllComments(productId)
        }
    }

    val thisProduct= mutableStateOf(EMPTY_PRODUCT)
    val ProductResponseComments= mutableStateOf(EMPTY_PRODUCT)
    private fun loadProductFromCash(productId:Int){
        viewModelScope.launch (coroutinExceptionHandler){
             thisProduct.value=productRepository.getProductById(productId)
        }
    }

    private fun loadAllComments(productId: Int){
        viewModelScope.launch(coroutinExceptionHandler){
            ProductResponseComments.value=commentRepository.getAllComments(productId)
        }
    }


}

