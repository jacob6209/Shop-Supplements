package com.example.supplementsonlineshopproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.UUID

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Retrieve the JSON string value under the key "data"
        val jsonData = remoteMessage.data["data"]

        // Parse the JSON string using JSONObject
        val jsonObject = JSONObject(jsonData)

        // Retrieve "body" and "title" from the JSONObject
        val title = jsonObject.getString("title")
        val body = jsonObject.getString("body")
        showNotification(title, body)
        // Log the values
        Log.d("FCM", "Received message: $title, $body, $jsonData")

//        val title= remoteMessage.data["title"]
//        val body=remoteMessage.data.get("body")
//
//        Log.d("FCM", "Received message:$title,$body,${remoteMessage.data}")
//
//        val data=remoteMessage.data
//        for (key in data.keys){
//            val value=data[key]
//            val title=remoteMessage.data["data"]
//            Log.v("FCM2","key:$key,Value:$value")
//        }

//        val title= remoteMessage.data["title"]
//        val body=remoteMessage.data.get("body")
//
//         Log.d("FCM", "Received message:$title,$body,${remoteMessage.data}")
//
//        val data=remoteMessage.data
//         for (key in data.keys){
//             val value=data[key]
//             val title=remoteMessage.data["data"]
//            Log.v("FCM2","key:$key,Value:$value")

        // Check if message contains a data payload.
//        if (remoteMessage.data.isNotEmpty()) {
//            // Check if message contains a notification payload.
//            if (remoteMessage.notification != null) {
//                val body=remoteMessage.notification!!.body
//                val title=remoteMessage.notification!!.title
//                Log.d(TAG, "Message Notification Body: $body ,$title");
//            }
//        }
//
//
//        // Extract title and body from the data payload
//        val title = remoteMessage.data["title"]
//        val body = remoteMessage.data["body"]
//        val data=remoteMessage.data
////        val clickAction=remoteMessage.notification?.clickAction
//
//        Log.d("FCM", "Data Payload Title: $title, Body: $body,$data" )

//        try {
//            if (remoteMessage.notification != null) {
//
//                val title = remoteMessage.notification!!.title
//                val body  = remoteMessage.notification!!.body
//
//                if (title != null && body != null) {
//                    showNotification(title, body)
//                }
//                Log.d("FCM", "Data Payload Title: $title, Body: $body")
//
//            }
//        } catch(ex :Exception) {
//            Log.v("FCM" , ex.message ?: "null exception")
//        }

    }

    private fun showNotification(title: String?, body: String?) {
        val webUrl = "http://192.168.127.57:8000/admin/"
        // Create an explicit intent to open a web browser
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))


        // Create a PendingIntent for the notification
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = UUID.randomUUID().toString()

        // Create a notification builder
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_email)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Get the notification manager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if the Android version is Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Generate a random channel ID
            val channel = NotificationChannel(
                channelId,
                "staff_group",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val RandomId=(0..1000).random()
        // Show the notification
        notificationManager.notify(RandomId, notificationBuilder.build())
    }
}

