package com.example.supplementsonlineshopproject.model.repository.product

import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.data.SignUpResponse
import com.example.supplementsonlineshopproject.model.db.ProductDao
import com.example.supplementsonlineshopproject.model.net.ApiService
import retrofit2.Response

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getAllProducts(isInternetConnected: Boolean): List<ProductResponse> {
        try {
            if (isInternetConnected) {
                // Get data from the network
                val response: Response<List<ProductResponse>> = apiService.getAllProducts()
                if (response.isSuccessful) {
                    val products: List<ProductResponse>? = response.body()
                    products?.let {
                        // Insert or update products in the local database
                        productDao.insertOrUpdate(products)
                        // Return the list of products fetched from the API
                        return products
                    }
                } else {
                    return emptyList()
                }
            } else {
                // Get data from the local database
                return productDao.getAll()
            }
        } catch (e: Exception) {
            return emptyList()
        }
        return emptyList()
    }

    override suspend fun getAllAds(isInternetConnected: Boolean): List<AdsResponse> {
       try {
           if (isInternetConnected) {
               // get Data From Internet
               val response: Response<List<AdsResponse>> = apiService.getAds()
               if (response.isSuccessful) {
                   val ads: List<AdsResponse>? = response.body()
                   return ads!!
               }
               return emptyList()
           }
       }catch (e:Exception){
           return emptyList()
       }
        return emptyList()
    }

    override suspend fun getAllProductByCategory(category: String): List<ProductResponse> {
        return  productDao.getAllByCategory(category)
    }

    override suspend fun getProductById(productId: Int): ProductResponse {
            return productDao.getProductById(productId)
    }
}