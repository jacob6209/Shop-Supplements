package com.example.supplementsonlineshopproject.ui.features.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.ui.theme.YellowDark

@Composable
fun RatingBar(
    maxRating: Int = 5,
    currentRating: Int,
    onRatingChanged: ((Int) -> Unit)?=null,
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
                    .clickable { onRatingChanged?.invoke(i) }
                    .padding(1.dp)
            )
        }
    }
}

@Composable
fun CommentRatingBar(
    maxRating: Int = 5,
    currentRating: Int,
    onRatingChanged: ((Int) -> Unit)? = null,
    ratedStarColor: Color = YellowDark, // Color for rated stars
    unratedStarColor: Color = Color.Gray, // Color for unrated stars
    starSize: Dp = 15.dp // Size of the stars
) {
    Row {
        for (i in 1..maxRating) {
            val icon = if (i <= currentRating) Icons.Default.Star else Icons.TwoTone.Star
            val starColor = if (i <= currentRating) ratedStarColor else unratedStarColor
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier
                    .clickable { onRatingChanged?.invoke(i) }
                    .size(starSize)
            )
        }
    }
}
