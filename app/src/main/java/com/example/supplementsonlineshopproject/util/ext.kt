package com.example.supplementsonlineshopproject.util


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import com.example.supplementsonlineshopproject.model.data.Address
import kotlinx.coroutines.CoroutineExceptionHandler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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
        return "$readyToGo Tomans"

    }
    return "$oldPrice Tomans"
}


fun styleTime(timeInMillis:Long):String{
    val formatter=SimpleDateFormat("yyyy/mm/dd hh:ss")
    val calender=Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    return formatter.format(calender.time)
}

fun convertAddressListToMap(addressList: List<Address>): Map<String, String> {
    val addressMap = mutableMapOf<String, String>()
    if (addressList.isNotEmpty()) {
        val address = addressList[0] // Assuming you are only dealing with one address in the list
        addressMap["first_name"] = address.first_name
        addressMap["last_name"] = address.last_name
        addressMap["phone_number"] = address.phone_number
        addressMap["province"] = address.province
        addressMap["city"] = address.city
        addressMap["street"] = address.street
        addressMap["address"] = address.address
        addressMap["zip_code"] = address.zip_code
    }
    return addressMap
}
//fun setLocation(context: Context, lang: String) {
//    val locale = Locale(lang)
//    Locale.setDefault(locale)
//
//    val config = Configuration()
//    config.locale = locale
//
//    context.resources.updateConfiguration(config, context.resources.displayMetrics)
//
//    val editor = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
//    editor.putString("My_Lang", lang)
//    editor.apply()
//}

//fun loadLocation(context: Context) {
//    val sharedPreferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
//    val language = sharedPreferences.getString("My_Lang", "")
//    setLocation(context, language!!)
//}

