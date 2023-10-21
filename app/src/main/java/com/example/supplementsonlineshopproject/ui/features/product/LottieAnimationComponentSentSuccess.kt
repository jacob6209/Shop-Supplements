package com.example.supplementsonlineshopproject.ui.features.product

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.supplementsonlineshopproject.R

@Composable
fun LottieAnimationComponentSentSuccess() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_success)
    )
    LottieAnimation(modifier = Modifier.size(160.dp,160.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

}