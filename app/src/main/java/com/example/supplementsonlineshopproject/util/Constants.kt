package com.example.supplementsonlineshopproject.util

import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.model.data.ProductResponse

const val KEY_PRODUCT_ARG="productId"
const val KEY_CATEGORY_ARG = "categoryTitle"
const val BASE_URL="http://192.168.0.101:8000/"
const val VALUE_SUCCESS="true"
const val VALUE_NOT_SUCCESS="not_success"
val EMPTY_PRODUCT=ProductResponse(1,1.5,1,"", emptyList(),"",true,
    emptyList(),1,1.5,"","",true,1.5,"",1)


val CATEGORY= listOf(
    Pair(R.string.Protein, R.drawable.protein),
    Pair(R.string.Pre_Workout, R.drawable.pre_workout),
    Pair(R.string.Creatine, R.drawable.creatine),
    Pair(R.string.Boosters, R.drawable.test_boosters),
    Pair(R.string.Vitamins, R.drawable.vitamins_well_ness),
    Pair(R.string.Clothing, R.drawable.workout_clothing),
    )


val TAGS= listOf(
    "Newest",
    "Best Seller",
    "Most Visited",
    "Highest Quality"
)
const val PAYMENT_PAID="p"
const val PAYMENT_PENDING="pn"
const val PAYMENT_UNPAID="u"
const val PAYMENT_CANCEL="c"
