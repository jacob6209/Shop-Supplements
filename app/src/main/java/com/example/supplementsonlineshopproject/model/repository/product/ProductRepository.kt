package com.example.supplementsonlineshopproject.model.repository.product

import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.data.AdsResponse

interface ProductRepository {

suspend fun getAllProducts(isInternetConnected:Boolean) :List<ProductResponse>
suspend fun getAllAds(isInternetConnected:Boolean):List<AdsResponse>

suspend fun getAllProductByCategory(category:String):List<ProductResponse>

}