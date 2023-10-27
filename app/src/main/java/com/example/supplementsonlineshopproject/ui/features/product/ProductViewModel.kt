package com.example.supplementsonlineshopproject.ui.features.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supplementsonlineshopproject.model.data.CreateCartResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.cart.CartInMemory.cartId
import com.example.supplementsonlineshopproject.model.repository.cart.CartRepository
import com.example.supplementsonlineshopproject.model.repository.comment.CommentRepository
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.util.EMPTY_PRODUCT
import com.example.supplementsonlineshopproject.util.coroutinExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response


class ProductViewModel(

    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository,
    private val cartRepository:CartRepository,

):ViewModel() {


    fun loadData(productId: Int, isInternetConnected: Boolean) {
        loadProductFromCash(productId)
        if (isInternetConnected) {
            loadAllComments(productId)
            loadBadgeNumber()
        }
    }

    val thisProduct = mutableStateOf(EMPTY_PRODUCT)
    val ProductResponseComments = mutableStateOf(EMPTY_PRODUCT)
    val isAddingProduct = mutableStateOf(false)
    val badgeNumber= mutableStateOf(0)
    private fun loadProductFromCash(productId: Int) {
        viewModelScope.launch(coroutinExceptionHandler) {
            thisProduct.value = productRepository.getProductById(productId)
        }
    }

    private fun loadBadgeNumber(){
        viewModelScope.launch(coroutinExceptionHandler) {
            badgeNumber.value=cartRepository.getCartSize(cartId!!)
        }
    }

    private fun loadAllComments(productId: Int) {
        viewModelScope.launch(coroutinExceptionHandler) {
            try {
                ProductResponseComments.value = commentRepository.getAllComments(productId)
            } catch (e: NoSuchElementException) {
                ProductResponseComments.value = EMPTY_PRODUCT
            } catch (e: Exception) {
                throw CustomException("Custom error message: ${e.message}")
            }
        }
    }

    class CustomException(message: String) : Exception(message)

    fun addNewComment(
        id: Int,
        name: String,
        body: String,
        rating: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(coroutinExceptionHandler) {
            commentRepository.AddNewCommentResponse(id, name, body, rating, onSuccess, onError)
            delay(100)
            ProductResponseComments.value = commentRepository.getAllComments(id)
        }
    }


    fun addProductToCart(cartId: String?,productId: Int, AddingToCart: (String) -> Unit) {
        viewModelScope.launch(coroutinExceptionHandler) {
            isAddingProduct.value = true
            val result = cartRepository.addToCart(cartId!!,productId.toString())
            delay(2000)
            isAddingProduct.value = false

            if (result.isSuccessful) {
                AddingToCart("Product Added To Cart")
            } else if (result.code()==400) {
                AddingToCart("Sorry, not enough stock available for this product!!")
            }
        }
    }

    fun getProductCartId(GetCartId:(String)->Unit) {
        viewModelScope.launch(coroutinExceptionHandler) {
            try {
                val result = cartRepository.createCart()
                if (!result.isNullOrBlank()) {
                    GetCartId.invoke(result)
                } else {
                    GetCartId.invoke("Failed To Get Cart Id")
//                    throw CustomException("Error in Create Cart,Please Try Again")
                }
            } catch (e: Exception) {
                throw CustomException("Something Went Wrong ,Please Try Again ${e.message}")
            }
        }
    }

    fun loadCartId() {
        cartRepository.loadCartId()
    }

    fun getCartIdFromCach(): String? {
        return cartRepository.getCartId()
    }

    fun saveCartId(cartId: String?) {
        cartRepository.saveCartId(cartId)
    }

}



