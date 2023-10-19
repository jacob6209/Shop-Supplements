package com.example.supplementsonlineshopproject.ui.features.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.comment.CommentRepository
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.util.EMPTY_PRODUCT
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class ProductViewModel(

    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository,

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
            try{
                ProductResponseComments.value=commentRepository.getAllComments(productId)
            }catch (e: NoSuchElementException){
                ProductResponseComments.value = EMPTY_PRODUCT
            }catch (e:Exception){
                throw CustomException("Custom error message: ${e.message}")
            }
        }
    }
    class CustomException(message: String) : Exception(message)

    fun addNewComment(id:Int,name:String,body:String,rating:Int,onSuccess: () -> Unit,onError: (String) -> Unit){
        viewModelScope.launch(coroutinExceptionHandler) {
            commentRepository.AddNewCommentResponse(id,name,body,rating, onSuccess,onError)
            delay(100)
            ProductResponseComments.value=commentRepository.getAllComments(id)
        }
    }


}

