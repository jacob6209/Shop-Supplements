package com.example.supplementsonlineshopproject.ui.features.product

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.model.data.Comment
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.repository.cart.CartInMemory
import com.example.supplementsonlineshopproject.model.repository.cart.CartInMemory.saveCartId
import com.example.supplementsonlineshopproject.ui.theme.BackgroundMain
import com.example.supplementsonlineshopproject.ui.theme.DIMENS_12dp
import com.example.supplementsonlineshopproject.ui.theme.DIMENS_16dp
import com.example.supplementsonlineshopproject.ui.theme.MainAppTheme
import com.example.supplementsonlineshopproject.ui.theme.Shapes
import com.example.supplementsonlineshopproject.ui.theme.priceBackground
import com.example.supplementsonlineshopproject.util.BASE_URL
import com.example.supplementsonlineshopproject.util.MyScreens
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.lerp
import com.example.supplementsonlineshopproject.util.stylePrice
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import getTimeAgo
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.absoluteValue


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    MainAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = BackgroundMain
        ) {
            ProductScreen(1)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductScreen(productId: Int) {
    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<ProductViewModel>()
    var showAnimation by remember { mutableStateOf(false) }
    viewModel.loadData(productId, NetworkChecker(context).isInternetConnected)
    viewModel.loadCartId()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter

    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 58.dp)
            ) {
                ProductToolbar(
                    productName = "Datail",
                    badgeNumber = viewModel.badgeNumber.value,
                    OnBackClicked = { navigation.popBackStack() },
                    OnCartClicked = {
                        if (NetworkChecker(context).isInternetConnected) {
                            navigation.navigate(MyScreens.CartScreen.route)
                        } else {
                            Toast.makeText(
                                context,
                                "Check Your Internet Connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
               val allComments= if (NetworkChecker(context).isInternetConnected)  viewModel.ProductResponseComments.value.comments else listOf()
//                val allComments: List<Comment> = viewModel.ProductResponseComments.value.comments
                val sortedComments = allComments.sortedByDescending { it.id }
                ProductItem(
                    comments = sortedComments,
                    data = viewModel.thisProduct.value,
                    OnCategoryClicked = {
                        navigation.navigate(MyScreens.CategoryScreen.route + "/" + it)
                    }, modifier = Modifier,
                    OnAddNewComment = { name, body, rating ->
                        viewModel.addNewComment(productId, name, body, rating, onSuccess = {
                            showAnimation = true
                        Toast.makeText(context, "Comment Added Successfully", Toast.LENGTH_SHORT).show()
                        }, onError = { error ->
                            Toast.makeText(context, "Failed to add : $error", Toast.LENGTH_SHORT)
                                .show()
                        })
                    }

                )

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        16.dp
                    ),
                contentAlignment = Alignment.Center
            ) {

                if (showAnimation) {
                    LottieAnimationComponentSentSuccess()
                }
                LaunchedEffect(showAnimation) {
                    delay(2000)
                    showAnimation = false
                }
            }
        }

        AddToCart(viewModel.thisProduct.value.unit_price, viewModel.isAddingProduct.value) {

            if (NetworkChecker(context).isInternetConnected) {
                val cartIdInCachMemo = viewModel.getCartIdFromCach()
                if (cartIdInCachMemo == null) {
                    try{
                    viewModel.getProductCartId() {
                        viewModel.saveCartId(it)
                    }
                    }catch (e:Exception){
                        throw ProductViewModel.CustomException("Error In Get Id Cart ${e.message}")
                    }
                }else{
                    viewModel.addProductToCart(cartIdInCachMemo,productId){
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }

                }

            }else{
                Toast.makeText(context, "Check Your Internet Connection Please", Toast.LENGTH_SHORT).show()
            }


        }



    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentBody(comment: Comment) {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTimeString = comment.datetime_created ?: "Invalid Date"
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)
    val formattedDateTime = getTimeAgo(dateTime)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        elevation = 1.dp,
        border = BorderStroke(0.dp, color = Color.LightGray),
        shape = Shapes.large
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = comment.name,
                    style = TextStyle(fontSize = 12.sp),
                    fontWeight = FontWeight.Bold
                )
                CommentRatingBar(currentRating = comment.rating)
            }
            Text(
                text = formattedDateTime,
                style = TextStyle(fontSize = 9.sp),
                color = Color.LightGray,
                fontWeight = FontWeight.Light
            )
            Text(
                text = comment.body,
                modifier = Modifier.padding(top = 10.dp),
                style = TextStyle(fontSize = 14.sp)
            )
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductComment(
    comment: List<Comment>,
    AddNewComment: (name: String, body: String, rating: Int) -> Unit
) {
    val ShowCommentDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (comment.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(

                text = "Comments",
                style = (TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium))
            )
            TextButton(onClick = {
                if (NetworkChecker(context).isInternetConnected) {
                    ShowCommentDialog.value = true
                } else {
                    Toast.makeText(
                        context,
                        "Please Check Your Internet Connection!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(
                    text = "Add Comment",
                    style = TextStyle(fontSize = 14.sp)

                )
            }

        }

        comment.forEach {
            CommentBody(it)
        }
    } else {
        TextButton(onClick = {
            if (NetworkChecker(context).isInternetConnected) {
                ShowCommentDialog.value = true
            } else {
                Toast.makeText(
                    context,
                    "Please Check Your Internet Connection!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text(
                text = "Add Comment",
                style = TextStyle(fontSize = 14.sp)

            )
        }

    }

    if (ShowCommentDialog.value) {
        AddNewCommentDialog(
            OnDismiss = { ShowCommentDialog.value = false },
            OnPositiveClicked = { name, body, rating ->
                AddNewComment.invoke(name, body, rating)
            }
        )
    }


}

@Composable
fun AddNewCommentDialog(
    OnDismiss: () -> Unit,
    OnPositiveClicked: (name: String, body: String, rating: Int) -> Unit

) {
    val userComment = remember { mutableStateOf("") }
    val userName = remember { mutableStateOf("") }
    val userRating = remember { mutableStateOf(4) }


    val context = LocalContext.current
    Dialog(onDismissRequest = { OnDismiss }) {
        Card(
            modifier = Modifier
                .fillMaxHeight(.65f),
            elevation = 8.dp,
            shape = Shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Write Your Comment ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )
//                Spacer(modifier = Modifier.height(2.dp))
                MainTextComment(
                    singleLine = true,
                    maxLength = 20,
                    edtValue = userName.value,
                    hint = "Name"
                ) {
                    userName.value = it
                }
//                Spacer(modifier = Modifier.height(2.dp))
                MainTextComment(
                    singleLine = false,
                    maxLength = 200,
                    edtValue = userComment.value,
                    hint = "Your Comment"
                ) {
                    userComment.value = it
                }
//                =====================================
//                CustomRatingBar(modifier = Modifier, rating = 4.5f, spaceBetween = 5.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Rate:",
                        fontWeight = FontWeight.Bold
                    )
                    RatingBar(
                        currentRating = userRating.value,
                        onRatingChanged = { userRating.value = it })
                }

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { OnDismiss.invoke() }) { Text(text = "Cancel") }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        if (userComment.value.isNotEmpty() && userComment.value.isNotBlank() && userName.value.isNotEmpty() && userName.value.isNotBlank()) {
                            if (NetworkChecker(context).isInternetConnected) {
                                OnPositiveClicked.invoke(
                                    userName.value,
                                    userComment.value,
                                    userRating.value
                                )
                                OnDismiss.invoke()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Check Your Internet Connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Comment Can Not be Empty,Write Something...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductItem(
    comments: List<Comment>,
    data: ProductResponse,
    OnCategoryClicked: (String) -> Unit,
    modifier: Modifier,
    OnAddNewComment: (name: String, body: String, rating: Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        ProductDesign(data, OnCategoryClicked, modifier)
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
        ProdactDetail(data, comments.size.toString())
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
        ProductComment(comments, OnAddNewComment)

    }
}

@Composable
fun ProdactDetail(data: ProductResponse, CommentNumber: String) {
    val context= LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.rating1),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Text(
                text = "${data.average_rating} Rate",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 12.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.comment1),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            val commentText=if(NetworkChecker(context).isInternetConnected) "$CommentNumber Comments" else "No Internet Connection!"
            Text(
                text = commentText,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 12.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.stock2),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Text(
                text = "${data.inventory} Stock",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 12.sp
            )
        }


    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDesign(data: ProductResponse, OnCategoryClicked: (String) -> Unit, modifier: Modifier) {
    SliderBanner(data, modifier)
    Text(
        modifier = Modifier.padding(top = 14.dp),
        text = data.title,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
    )
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = data.description,
        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Justify)
    )

    TextButton(onClick = { OnCategoryClicked.invoke(data.category_title) }) {
        Text(text = "#" + data.category_title, style = TextStyle(fontSize = 13.sp))
    }

}

@Composable
fun ProductToolbar(
    productName: String,
    badgeNumber: Int,
    OnBackClicked: () -> Unit,
    OnCartClicked: () -> Unit

) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(onClick = { OnBackClicked.invoke() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )

            }
        },
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp),
                text = productName,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(
                modifier = Modifier.padding(end = 6.dp),
                onClick = { OnCartClicked.invoke() },
            ) {
                if (badgeNumber == 0) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                } else {
                    BadgedBox(badge = { Badge { Text(text = badgeNumber.toString()) } }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            }
        }
    )
}

@Composable
fun AddToCart(
    price: Double,
    isAddingProduct: Boolean,
    onCartClicked: () -> Unit
) {
    val configuration= LocalConfiguration.current
    val fraction =if (configuration.orientation==Configuration.ORIENTATION_LANDSCAPE) 0.15f else 0.08f

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Button(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(182.dp, 40.dp),
                onClick = { onCartClicked.invoke() }
            )
            {

                if (isAddingProduct) {
                    DotsPulsing()
                } else {
                    Text(
                        text = "Add Product To Cart",
                        color = Color.White,
                        modifier = Modifier.padding(2.dp),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    )
                }

            }

//            val formattedPrice = NumberFormat.getNumberInstance(Locale.getDefault()).format(price.toInt())
            Surface(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(Shapes.large),
                color = priceBackground

            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    ),
                    text = stylePrice(price.toInt().toString()),
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                )
            }
        }
    }
}

//---------------------------------------------------------
@ExperimentalPagerApi
@Composable
fun SliderBanner(
    data: ProductResponse,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column {
        HorizontalPager(
            count = data.images.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = DIMENS_16dp),
            modifier = modifier
                .fillMaxSize()
        ) { page ->
            Card(
                shape = RoundedCornerShape(DIMENS_12dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {

                AsyncImage(
                    model = BASE_URL + data.images[page].image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = Shapes.medium)
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(DIMENS_16dp)
        )
    }
}


@Composable
fun MainTextComment(
    singleLine: Boolean,
    maxLength: Int = 200,
    edtValue: String,
    hint: String,
    onValueChange: (String) -> Unit,

    ) {
    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = singleLine,
        maxLines = 2,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        placeholder = { Text(text = "Write Something...") },
        modifier = Modifier
            .fillMaxWidth(0.9f),
        shape = Shapes.medium,
    )
}

//                  For Animation DotsPulsing
//----------------------------------------------------------------------------------------------
const val numberOfDots =5
val dotSize = 24.dp
val dotColor: Color = Color.White
const val delayUnit = 200
const val duration = numberOfDots * delayUnit
val spaceBetween = 2.dp

@Composable
fun DotsPulsing() {

    @Composable
    fun Dot(scale: Float) {
        Spacer(
            Modifier
                .size(dotSize)
                .scale(scale)
                .background(
                    color = dotColor,
                    shape = CircleShape
                )
        )
    }

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateScaleWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = delayUnit * numberOfDots
            0f at delay with LinearEasing
            1f at delay + delayUnit with LinearEasing
            0f at delay + duration
        })
    )

    val scales = arrayListOf<State<Float>>()
    for (i in 0 until numberOfDots) {
        scales.add(animateScaleWithDelay(delay = i * delayUnit))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        scales.forEach {
            Dot(it.value)
            Spacer(Modifier.width(spaceBetween))
        }
    }
}




