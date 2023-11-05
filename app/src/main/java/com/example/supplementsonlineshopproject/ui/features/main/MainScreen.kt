package com.example.supplementsonlineshopproject.ui.features.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.CheckOut
import com.example.supplementsonlineshopproject.model.data.CheckoutOrder
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.ui.theme.BackgroundMain
import com.example.supplementsonlineshopproject.ui.theme.Blue
import com.example.supplementsonlineshopproject.ui.theme.CardViewBackgroundItem
import com.example.supplementsonlineshopproject.ui.theme.MainAppTheme
import com.example.supplementsonlineshopproject.ui.theme.Shapes
import com.example.supplementsonlineshopproject.util.BASE_URL
import com.example.supplementsonlineshopproject.util.CATEGORY
import com.example.supplementsonlineshopproject.util.MyScreens
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.PAYMENT_PAID
import com.example.supplementsonlineshopproject.util.PAYMENT_PENDING
import com.example.supplementsonlineshopproject.util.PAYMENT_UNPAID
import com.example.supplementsonlineshopproject.util.TAGS
import com.example.supplementsonlineshopproject.util.stylePrice
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import org.koin.core.parameter.parametersOf


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = BackgroundMain
        ) {
            MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    val context = LocalContext.current
    val uiController = rememberSystemUiController()
    val navigation = getNavController()
    val viewModel = getNavViewModel<MainViewModel>(
        parameters = { parametersOf(NetworkChecker(context).isInternetConnected) }
    )

    if (NetworkChecker(context).isInternetConnected) {
        viewModel.loadBadgeNumber()
    }
    if (viewModel.getPaymentStatus()== PAYMENT_PENDING){
        if (NetworkChecker(context).isInternetConnected){
            viewModel.getCheckoutData()
        }
    }

    Scaffold(
        topBar = {
            TopToolbar(badgeNumber = viewModel.badgeNumber.value,
                onCartClicked = {
                    if (NetworkChecker(context).isInternetConnected){
                    navigation.navigate(MyScreens.CartScreen.route)
                    }else{
                        Toast.makeText(context, "Check Your Internet Connection ", Toast.LENGTH_SHORT).show()
                    }
                }, onProfilClicked = {
                    navigation.navigate(MyScreens.ProfileScreen.route)
                })
        }

    ) { innerPadding ->    /* avoid overlapping with the system insets (like the status bar and navigation bar)    */
        SideEffect {
            uiController.setStatusBarColor(Color.White)
        }
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(bottom = 16.dp)
            ) {
                if (viewModel.showProgressBar.value) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Blue
                    )
                }
                Categorybar(CATEGORY) {
                    navigation.navigate(MyScreens.CategoryScreen.route + "/" + it)
                }
                val productDataState = viewModel.dataProducts
                val adsDataState = viewModel.dataAds

                ProductSubjectList(TAGS, productDataState.value, adsDataState.value) {
                    navigation.navigate(MyScreens.ProductScreen.route + "/" + it)
                }
//            ProductSubject()
//            BigPictureTablighat()
//            ProductSubject()
//            ProductSubject()
            }

            if (viewModel.showPaymentDialog.value){
                PaymentResultDialog(
                    checkoutResult =viewModel.checkoutData.value,

                    onDismiss = {
                        viewModel.showPaymentDialog.value=false
                        viewModel.setPaymentStatus(PAYMENT_UNPAID)
                    }
                )
            }
        }

    }
}

@Composable
private fun PaymentResultDialog(
    checkoutResult: CheckoutOrder,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 8.dp,
            shape = Shapes.medium
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Payment Result",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Main Data
                if (checkoutResult.status== PAYMENT_PAID) {
                    AsyncImage(
                        model = R.drawable.success_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(110.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Payment was successful!", style = TextStyle(fontSize = 16.sp))
//                    Text(
//                        text = "Purchase Amount: " + stylePrice(
//                            (checkoutResult.order!!.amount).substring(
//                                0,
//                                (checkoutResult.order!!.amount).length - 1
//                            )
//                        )
//                    )

                } else {

                    AsyncImage(
                        model = R.drawable.fail_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .padding(top = 6.dp, bottom = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Payment was not successful!", style = TextStyle(fontSize = 16.sp))
//                    Text(
//                        text = "Purchase Amount: " + stylePrice(
//                            (checkoutResult.order!!.amount).substring(
//                                0,
//                                (checkoutResult.order.amount).length - 1
//                            )
//                        )
//                    )

                }

                // Ok Button
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onDismiss) {
                        Text(text = "ok")
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                }
            }
        }
    }
}

//<----------------------------------------------------------------------------------->
@Composable
fun ProductSubjectList(
    tags: List<String>,
    products: List<ProductResponse>,
    ads: List<AdsResponse>,
    onProductClicked: (String) -> Unit,
) {
    val context = LocalContext.current
    if (products.isNotEmpty()) {
        Column {
            tags.forEachIndexed { it, _ ->
                val withTagData = products.filter { product -> product.tags == tags[it] }
                ProductSubject(tags[it], withTagData.shuffled(), onProductClicked)
                if (ads.size >= 2)
                    if (it == 1 || it == 2)
                        BigPictureTablighat(ads[it - 1], onProductClicked)

            }
        }
    }
//    }
}


//<----------------------------------------------------------------------------------->
@Composable
fun ProductSubject(
    subject: String,
    data: List<ProductResponse>,
    onProductClicked: (String) -> Unit
) {
    Column(modifier = Modifier.padding(top = 32.dp))
    {
        Text(
            text = subject,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6
        )

        ProductBar(data, onProductClicked)
    }
}

@Composable
fun ProductBar(data: List<ProductResponse>, onProductClicked: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    )
    {
        items(data.size) {

            ProductItem(data[it], onProductClicked)
        }
    }
}

@Composable
fun ProductItem(product: ProductResponse, onProductClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onProductClicked.invoke((product.id).toString()) },
        elevation = 4.dp,
        shape = Shapes.medium
    ) {
        Column {


            AsyncImage(
                model = BASE_URL + product.images.firstOrNull()?.image,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null,


                )

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = product.title.take(25),
                    style = TextStyle(fontSize = 12.sp),
                    fontWeight = FontWeight.Medium
                )
//                val formattedPrice =
//                    NumberFormat.getNumberInstance(Locale.getDefault()).format(product.unit_price)
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stylePrice(product.unit_price.toInt().toString()),
                    style = TextStyle(fontSize = 11.sp),
                )
                Text(
                    text = "${product.soled_item} Sold",
                    style = TextStyle(color = Color.Gray, fontSize = 9.sp),

                    )

            }

        }

    }
}

//<----------------------------------------------------------------------------------->
@Composable
fun BigPictureTablighat(ads: AdsResponse, onProductClicked: (String) -> Unit) {

    AsyncImage(
        model = ads.product.images.lastOrNull()?.image,
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .clip(shape = Shapes.medium)
            .clickable { onProductClicked.invoke((ads.product.id).toString()) },
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}

//<----------------------------------------------------------------------------------->
@Composable
fun TopToolbar(
    badgeNumber: Int,
    onCartClicked: () -> Unit,
    onProfilClicked: () -> Unit
) {

    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp,
        title = { Text(text = "Jac Supplement") },
        actions = {
            IconButton(
                onClick = { onCartClicked.invoke() },
            ) {
                if (badgeNumber == 0) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                } else {
                    BadgedBox(badge = { Badge { Text(text = badgeNumber.toString()) } }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            }
            IconButton(onClick = { onProfilClicked.invoke() }) {
                Icon(Icons.Default.Person, null)
            }
        }
    )
}

//<----------------------------------------------------------------------------------->
@Composable
fun Categorybar(CategoryList: List<Pair<String, Int>>, onCategoryClicked: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(CategoryList.size) {
            CategoryItem(CategoryList[it], onCategoryClicked)
        }
    }
}

@Composable
fun CategoryItem(subject: Pair<String, Int>, onCategoryClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onCategoryClicked(subject.first) },
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Surface(
            shape = Shapes.medium,
            color = CardViewBackgroundItem,
        ) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(128.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = subject.second),
                contentDescription = null,
            )
        }
        Text(
            text = subject.first,
            modifier = Modifier.padding(top = 4.dp),
            style = TextStyle(color = Color.Gray)
        )
    }
}


//@Composable
//fun DuniCartItem(
//    data: Product,
//    isChangingNumber: Pair<String, Boolean>,
//    OnAddItemClicked: (String) -> Unit,
//    OnRemoveItemClicked: (String) -> Unit,
//    OnItemClicked: (String) -> Unit
//) {
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
//            .clickable { OnItemClicked.invoke(data.productId) },
//        elevation = 4.dp,
//        shape = Shapes.large
//    ) {
//
//        Column {
//
//            AsyncImage(
//                model = data.imgUrl,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//                Column(
//                    modifier = Modifier.padding(10.dp)
//                ) {
//
//                    Text(
//                        text = data.name,
//                        style = TextStyle(
//                            fontSize = 15.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                    )
//
//                    Text(
//                        modifier = Modifier.padding(top = 4.dp),
//                        text = "From " + data.category + " Group",
//                        style = TextStyle(fontSize = 14.sp)
//                    )
//
//                    Text(
//                        modifier = Modifier.padding(top = 18.dp),
//                        text = "Product authenticity guarantee",
//                        style = TextStyle(fontSize = 14.sp)
//                    )
//
//                    Text(
//                        modifier = Modifier.padding(top = 4.dp),
//                        text = "Available in stock to ship",
//                        style = TextStyle(fontSize = 14.sp)
//                    )
//
//
//                    Surface(
//                        modifier = Modifier
//                            .padding(top = 18.dp, bottom = 6.dp)
//                            .clip(Shapes.large),
//                        color = PriceBackground
//                    ) {
//
//                        Text(
//                            modifier = Modifier.padding(
//                                top = 6.dp,
//                                bottom = 6.dp,
//                                start = 8.dp,
//                                end = 8.dp
//                            ),
//                            text = stylePrice(
//                                (data.price.toInt() * (data.quantity ?: "1").toInt()).toString()
//                            ),
//                            style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
//                        )
//                    }
//                }
//
//                Surface(
//                    modifier = Modifier
//                        .padding(bottom = 14.dp, end = 8.dp)
//                        .align(Alignment.Bottom)
//                ) {
//
//
//                    Card(
//                        border = BorderStroke(2.dp, Blue)
//                    ) {
//
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//
//                            if (data.quantity?.toInt() == 1) {
//
//                                IconButton(onClick = { OnRemoveItemClicked.invoke(data.productId) }) {
//                                    Icon(
//                                        modifier = Modifier.padding(end = 4.dp, start = 4.dp),
//                                        imageVector = Icons.Default.Delete,
//                                        contentDescription = null
//                                    )
//                                }
//
//                            } else {
//
//                                IconButton(onClick = { OnRemoveItemClicked.invoke(data.productId) }) {
//                                    Icon(
//                                        modifier = Modifier.padding(end = 4.dp, start = 4.dp),
//                                        painter = painterResource(R.drawable.ic_minus),
//                                        contentDescription = null
//                                    )
//                                }
//
//                            }
//
//
//                            // size of product
//                            if (isChangingNumber.first == data.productId && isChangingNumber.second) {
//
//                                Text(
//                                    text = "...",
//                                    style = TextStyle(fontSize = 18.sp),
//                                    modifier = Modifier.padding(bottom = 12.dp)
//                                )
//
//                            } else {
//
//                                Text(
//                                    text = data.quantity ?: "1",
//                                    style = TextStyle(fontSize = 18.sp),
//                                    modifier = Modifier.padding(bottom = 4.dp)
//                                )
//
//                            }
//
//
//                            // add button +
//                            IconButton(onClick = { OnAddItemClicked.invoke(data.productId) }) {
//                                Icon(
//                                    modifier = Modifier.padding(end = 4.dp, start = 4.dp),
//                                    imageVector = Icons.Default.Add,
//                                    contentDescription = null
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}



