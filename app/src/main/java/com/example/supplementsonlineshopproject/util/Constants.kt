package com.example.supplementsonlineshopproject.util

import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.model.data.ProductResponse

const val KEY_PRODUCT_ARG="productId"
const val KEY_CATEGORY_ARG = "categoryTitle"
const val BASE_URL="http://192.168.1.4:8000/"
const val VALUE_SUCCESS="true"
const val VALUE_NOT_SUCCESS="not_success"
val EMPTY_PRODUCT=ProductResponse(1,1.5,1,"", emptyList(),"",true,
    emptyList(),1,1.5,"","",true,1.5,"",1)


val CATEGORY= listOf(
    Pair("Protein", R.drawable.protein),
    Pair("Pre-Workout", R.drawable.pre_workout),
    Pair("Creatine", R.drawable.creatine),
    Pair("Boosters", R.drawable.test_boosters),
    Pair("Vitamins", R.drawable.vitamins_well_ness),
    Pair("Clothing", R.drawable.workout_clothing),
    )


val TAGS= listOf(
    "Newest",
    "Best Seller",
    "Most Visited",
    "Highest Quality"
)