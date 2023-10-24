package com.example.supplementsonlineshopproject.model.repository.cart

object CartInMemory {
    var cartId:String?=null
        private set
    fun saveCartId(cartId:String?){
        this.cartId=cartId
    }
}