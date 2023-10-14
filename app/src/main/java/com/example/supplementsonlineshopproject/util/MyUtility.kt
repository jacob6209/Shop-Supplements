package com.example.supplementsonlineshopproject.util

import android.widget.Toast
import android.content.Context



    object MyUtility {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        fun truncateErrorMessage(message: String, maxLength: Int): String {
            return if (message.length > maxLength) {
                message.substring(0, maxLength - 3) + "..." // Truncate the message if it's too long
            } else {
                message // Return the original message if it's within the length limit
            }
        }
    }
fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}

