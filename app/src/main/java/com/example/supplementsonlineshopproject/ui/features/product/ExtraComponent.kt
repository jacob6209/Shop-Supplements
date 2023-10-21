
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeAgo(dateTime: LocalDateTime): String {
    val now = LocalDateTime.now()
    Log.d("TAG", "DateTimeStringNow: $now")
    Log.d("TAG", "DateTimeStringDatatime: $dateTime")
    val differenceMinutes = ChronoUnit.MINUTES.between(dateTime, now)
    Log.d("TAG", "DateTimeStringDIFF: $differenceMinutes")

    return when {
        differenceMinutes < 1 -> "Just now"
        differenceMinutes < 60 -> "$differenceMinutes minutes ago"
        else -> {
            val differenceHours = differenceMinutes / 60
            when {
                differenceHours < 24 -> "$differenceHours hours ago"
                differenceHours < 24 * 7 -> "${differenceHours / 24} days ago"
                differenceHours < 24 * 30 -> "${differenceHours / (24 * 7)} weeks ago"
                else -> "${differenceHours / (24 * 30)} months ago"
            }
        }
    }
}



//package com.example.supplementsonlineshopproject.ui.features.product
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.Icon
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Rect
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Outline
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun RatingStar(
//    rating: Float = 5f,
//    maxRating: Int = 5,
//    onStarClick: (Int) -> Unit,
//    isIndicator: Boolean = false
//) {
//    Row {
//        for (i in 1..maxRating) {
//            if (i <= rating.toInt()) {
//                // Full stars
//                Icon(
//                    imageVector = Icons.Default.Star,
//                    contentDescription = null,
//                    tint = MaterialTheme.colors.secondary,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clickable(!isIndicator) {
//                            onStarClick(i)
//                        }
//                )
//            } else if (i == rating.toInt() + 1 && rating % 1 != 0f) {
//                // Partial star
//                PartialStar(fraction = rating % 1)
//            } else {
//                // Empty stars
//                Icon(
//                    imageVector = Icons.Default.Star,
//                    contentDescription = null,
//                    tint = Color.Gray,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clickable(!isIndicator) {
//                            onStarClick(i)
//                        }
//                )
//            }
//        }
//    }
//}
//@Composable
//private fun PartialStar(fraction: Float) {
//    val customShape = FractionalClipShape(fraction)
//
//    Box {
//        Icon(
//            imageVector = Icons.Default.Star,
//            contentDescription = null,
//            tint = Color.Gray,
//            modifier = Modifier.size(24.dp)
//        )
//        Box(
//            modifier = Modifier
//                .graphicsLayer(
//                    clip = true,
//                    shape = customShape
//                )
//        ) {
//            Icon(
//                imageVector = Icons.Default.Star,
//                contentDescription = null,
//                tint = MaterialTheme.colors.secondary,
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
//}
//
//
//private class FractionalClipShape(private val fraction: Float) : Shape {
//    override fun createOutline(
//        size: Size,
//        layoutDirection: LayoutDirection,
//        density: Density
//    ): Outline {
//        return Outline.Rectangle(
//            rect = Rect(
//                left = 0f,
//                top = 0f,
//                right = size.width * fraction,
//                bottom = size.height
//            )
//        )
//    }
//}
//
////import android.graphics.Bitmap
////import android.graphics.Canvas
////import android.graphics.Paint
////import android.graphics.PorterDuff
////import android.graphics.PorterDuffXfermode
////import android.graphics.RectF
////import android.view.MotionEvent
////import androidx.compose.foundation.Canvas
////import androidx.compose.foundation.layout.Box
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.height
////import androidx.compose.foundation.layout.width
////import androidx.compose.runtime.*
////import androidx.compose.ui.ExperimentalComposeUiApi
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.geometry.Offset
////import androidx.compose.ui.graphics.ImageBitmap
////import androidx.compose.ui.graphics.asAndroidBitmap
////import androidx.compose.ui.graphics.drawscope.DrawScope
////import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
////import androidx.compose.ui.graphics.nativeCanvas
////import androidx.compose.ui.input.pointer.pointerInteropFilter
////import androidx.compose.ui.platform.LocalDensity
////import androidx.compose.ui.res.imageResource
////import androidx.compose.ui.unit.Dp
////import androidx.compose.ui.unit.dp
////import com.example.supplementsonlineshopproject.R
//
////@OptIn(ExperimentalComposeUiApi::class)
////@Composable
////fun CustomRatingBar(
////    modifier: Modifier = Modifier,
////    rating: Float,
////    onRatingChanged: (Float) -> Unit,
////    spaceBetween: Dp = 0.dp
////) {
////    val image = ImageBitmap.imageResource(id = R.drawable.star)
////    val imageFull = ImageBitmap.imageResource(id = R.drawable.star_full)
////
////    val totalCount = 5
////
////    val height = LocalDensity.current.run { image.height.toDp() }
////    val width = LocalDensity.current.run { image.width.toDp() }
////    val space = LocalDensity.current.run { spaceBetween.toPx() }
////    val totalWidth = width * totalCount + spaceBetween * (totalCount - 1)
////
////    var startX by remember { mutableStateOf(0f) }
////
////    Box(
////        modifier
////            .width(totalWidth)
////            .height(height)
////            .pointerInteropFilter { event ->
////                when (event.action) {
////                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
////                        val offsetX = event.x / LocalDensity.current.density
////                        val newRating = offsetX / (image.width + space)
////
////                        if (newRating in 0f..totalCount.toFloat()) {
////                            onRatingChanged(newRating)
////                        }
////                    }
////                }
////                true
////            }
////    ) {
////        Canvas(modifier = Modifier.fillMaxSize()) {
////            DrawRating(rating, image, imageFull, space)
////        }
////    }
////}
////
////@Composable
////private fun DrawScope.DrawRating(
////    rating: Float,
////    image: ImageBitmap,
////    imageFull: ImageBitmap,
////    space: Float
////) {
////    val totalCount = 5
////    val imageWidth = image.width.toFloat()
////    val imageHeight = size.height
////
////    for (i in 0 until totalCount) {
////        val start = imageWidth * i + space * i
////        drawImage(image, topLeft = Offset(start, 0f))
////    }
////
////    drawIntoCanvas { canvas ->
////        val end = imageWidth * totalCount + space * (totalCount - 1)
////        val start = rating * imageWidth
////        val size = end - start
////
////        canvas.nativeCanvas.run {
////            saveLayer(null, null)
////
////            for (i in 0 until totalCount) {
////                val starStart = imageWidth * i + space * i
////                // Destination
////                drawBitmap(
////                    imageFull.asAndroidBitmap(),
////                    starStart,
////                    0f,
////                    Paint().apply {
////                        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
////                    }
////                )
////            }
////
////            // Source
////            drawRect(
////                RectF(start, 0f, start + size, imageHeight),
////                Paint().apply {
////                    xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
////                }
////            )
////            restore()
////        }
////    }
////}
////
////
//
//
//
//
//
//
//
//
////@Composable
////fun CustomRatingBar(
////    modifier: Modifier = Modifier,
////    rating: Float,
////    spaceBetween: Dp = 0.dp
////) {
////
////    val image = ImageBitmap.imageResource(id = R.drawable.star)
////    val imageFull = ImageBitmap.imageResource(id = R.drawable.star_full)
////
////    val totalCount = 5
////
////    val height = LocalDensity.current.run { image.height.toDp() }
////    val width = LocalDensity.current.run { image.width.toDp() }
////    val space = LocalDensity.current.run { spaceBetween.toPx() }
////    val totalWidth = width * totalCount + spaceBetween * (totalCount - 1)
////
////
////    Box(
////        modifier
////            .width(totalWidth)
////            .height(height)
////            .drawBehind {
////                drawRating(rating, image, imageFull, space)
////            })
////}
////
////private fun DrawScope.drawRating(
////    rating: Float,
////    image: ImageBitmap,
////    imageFull: ImageBitmap,
////    space: Float
////) {
////
////    val totalCount = 5
////
////    val imageWidth = image.width.toFloat()
////    val imageHeight = size.height
////
////    val reminder = rating - rating.toInt()
////    val ratingInt = (rating - reminder).toInt()
////
////    for (i in 0 until totalCount) {
////
////        val start = imageWidth * i + space * i
////
////        drawImage(
////            image = image,
////            topLeft = Offset(start, 0f)
////        )
////    }
////
////    drawWithLayer {
////        for (i in 0 until totalCount) {
////            val start = imageWidth * i + space * i
////            // Destination
////            drawImage(
////                image = imageFull,
////                topLeft = Offset(start, 0f)
////            )
////        }
////
////        val end = imageWidth * totalCount + space * (totalCount - 1)
////        val start = rating * imageWidth + ratingInt * space
////        val size = end - start
////
////        // Source
////        drawRect(
////            Color.Transparent,
////            topLeft = Offset(start, 0f),
////            size = Size(size, height = imageHeight),
////            blendMode = BlendMode.SrcIn
////        )
////    }
////}
//
////private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
////    with(drawContext.canvas.nativeCanvas) {
////        val checkPoint = saveLayer(null, null)
////        block()
////        restoreToCount(checkPoint)
////    }
////}