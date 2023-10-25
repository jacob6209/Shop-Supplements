package com.example.supplementsonlineshopproject.util

import android.graphics.Paint.Style
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import java.text.SimpleDateFormat
import java.util.Calendar

val coroutinExceptionHandler= CoroutineExceptionHandler { _, throwable ->
    Log.v("error","Error->"+throwable.message)
}
fun stylePrice(oldPrice: String):String{
    if (oldPrice.length>3){
        val reversed=oldPrice.reversed()
        var newPrice=""
        for (i in oldPrice.indices){
            newPrice+=reversed[i]
            if ((i+1)%3==0){
                newPrice+=','
            }
        }
        val readyToGo=newPrice.reversed()
        if (readyToGo.first() == ','){
            return readyToGo.substring(1)+" Tomans"
        }
        return readyToGo + " Tomans"

    }
    return oldPrice + " Tomans"
}


fun styleTime(timeInMillis:Long):String{
    val formatter=SimpleDateFormat("yyyy/mm/dd hh:ss")
    val calender=Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    return formatter.format(calender.time)
}
