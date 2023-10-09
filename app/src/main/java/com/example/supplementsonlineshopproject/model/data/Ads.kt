package com.example.supplementsonlineshopproject.model.data

data class AdsResponse(
    val ad_id: Int,
    val product: Productx
)
data class Productx(
    val id: Int,
    val images: List<Imagex>
)
data class Imagex(
    val id: Int,
    val image: String
)