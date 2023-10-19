package com.example.supplementsonlineshopproject.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutinExceptionHandler= CoroutineExceptionHandler { _, throwable ->
    Log.v("error","Error->"+throwable.message)
}
