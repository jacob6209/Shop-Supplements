package com.example.supplementsonlineshopproject.ui.features.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.supplementsonlineshopproject.R

@Composable
fun RatingBar(
    maxRating: Int = 5,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    starsColor: Color = Color.Blue
){
        val Star = ImageBitmap.imageResource(id = R.drawable.star)
        val Star_full = ImageBitmap.imageResource(id = R.drawable.star_full)
    Row {
        for (i in 1..maxRating) {
            Image(
                bitmap = if (i <= currentRating) Star_full else Star,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onRatingChanged(i) }
                    .padding(1.dp)
            )
        }
    }
}