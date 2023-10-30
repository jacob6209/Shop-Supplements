package com.example.supplementsonlineshopproject.ui.features.cart

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Paint.Style
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.model.data.UserCartInfo
import com.example.supplementsonlineshopproject.model.repository.cart.CartInMemory
import com.example.supplementsonlineshopproject.ui.features.main.MainViewModel
import com.example.supplementsonlineshopproject.ui.features.product.ProductViewModel
import com.example.supplementsonlineshopproject.ui.features.profile.AddUserLocationDataDialog
import com.example.supplementsonlineshopproject.ui.theme.Blue
import com.example.supplementsonlineshopproject.ui.theme.Shapes
import com.example.supplementsonlineshopproject.ui.theme.priceBackground
import com.example.supplementsonlineshopproject.util.BASE_URL
import com.example.supplementsonlineshopproject.util.MyScreens
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.PAYMENT_PENDING
import com.example.supplementsonlineshopproject.util.stylePrice
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel


@Composable
fun CartScreen() {
    val context = LocalContext.current
    val getDialogState = remember { mutableStateOf(false) }
    val viewModel = getNavViewModel<CartViewModel>()
    val navigation = getNavController()
    val errorMessage by remember { viewModel.errorMessage }
//    viewModel.loadCartdata()

    LaunchedEffect(Unit) {
        viewModel.loadCartdata()
    }
    if (errorMessage != null) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
//        Text(text = errorMessage!!, color = Color.Red) // Example: Display error message in red color.
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 74.dp)
        )
        {
            CartToolbar(
                OnBackClicked = { navigation.popBackStack() },
                OnProfileClicked = { navigation.navigate(MyScreens.ProfileScreen.route) }
            )
            if (viewModel.productList.value.isNotEmpty()) {
                CartItemList(
                    dataCartInfo = viewModel.productList.value,
                    data = viewModel.productdata.value,
                    isChangingNumber = viewModel.isChangingNumber.value,
                    OnAddItemClicked = {
                        viewModel.addItem(
                            viewModel.cartRepository.getCartId()!!,
                            it
                        )
                    },
                    OnRemoveItemClicked = {
                        viewModel.removeItem(
                            viewModel.cartRepository.getCartId()!!,
                            it
                        )
                    },
                    OnItemClicked = { navigation.navigate(MyScreens.ProductScreen.route + "/" + it) }
                )

            } else {

                NoDataAnimation()

            }

        }
        PurchaseAll(totalprice = viewModel.totalPrice.value.toString()) {
            if (viewModel.productList.value.isNotEmpty()) {
                val locationData = viewModel.getUserLocation()
                val locationDataF_name = viewModel.getFirstName()
                val locationDataL_name = viewModel.getLastName()
                val locationData_Phone = viewModel.getPhone_number()
                val locationData_Province = viewModel.getFirstName()
                val locationData_City = viewModel.getCity()
                val locationData_Street = viewModel.getStreet()

                if (locationData.first.isEmpty() || locationData.second.isEmpty() || locationDataF_name.isEmpty() || locationDataL_name.isEmpty() ||
                    locationData_Phone.isEmpty() || locationData_Province.isEmpty() || locationData_City.isEmpty() || locationData_Street.isEmpty()
                ) {
                    getDialogState.value = true

                } else {
//                    pardakht
                    viewModel.purchaseAll(
                        cartId =viewModel.cartRepository.getCartId()!!,
                        locationData.first,
                        locationData.second,
                        locationDataF_name,
                        locationDataL_name,
                        locationData_Phone,
                        locationData_Province,
                        locationData_City,
                        locationData_Street
                    ) {success,linke->
                        if (success){
                            viewModel.setPaymentStatus(PAYMENT_PENDING)
                            Toast.makeText(context, "pay using zarinpal...", Toast.LENGTH_SHORT).show()
//                            to doooooooooooooooooooooo
//                            viewModel.paymentProcess(id)
                            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(linke))
                            context.startActivities(arrayOf(intent))
                        }else{
                            Toast.makeText(context, "Problem in Payment", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            } else {
                Toast.makeText(context, "Please add some product first...", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if(getDialogState.value){
            AddUserLocationDataDialog(
                showSaveLocation = true,
                onDismiss = {getDialogState.value=false },
                onSubmitClicked = {address,postalcode,f_name,l_name,phone,province,city,street,isChecked->
                        if (NetworkChecker(context).isInternetConnected){
                            if (isChecked){
                                viewModel.setUserLocation(address,postalcode,f_name,l_name,phone,province,city,street)
                            }

//                            pardakht
                            viewModel.purchaseAll(
                                cartId =viewModel.cartRepository.getCartId()!!,
                                 address,
                                postalcode,
                                f_name,
                                l_name,
                                phone,
                                province,
                                city,
                                street
                            ) {success,id->
                                if (success){
                                    viewModel.setPaymentStatus(PAYMENT_PENDING)
                                    val intent=Intent(Intent.ACTION_VIEW, Uri.parse(id))
                                    context.startActivities(arrayOf(intent))
                                }else{
                                    Toast.makeText(context, "Problem in Payment", Toast.LENGTH_SHORT).show()
                                }

                            }

                        }else{
                            Toast.makeText(context, "Check your internet connection please!", Toast.LENGTH_SHORT).show()
                        }

                }
            )
        }
    }

}


@Composable
fun PurchaseAll(
    totalprice: String,
    onpurchaseClicked: () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val fraction =if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.15f else 0.07f
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(182.dp, 40.dp),
                onClick = {
                    if (NetworkChecker(context).isInternetConnected){
                        onpurchaseClicked.invoke()
                    }else{
                        Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
                    }
                }

            ) {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "Let's Purchase !",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                )

            }

            Surface(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(shape = Shapes.large),
                color = priceBackground

            ) {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
                    text = "total: ${stylePrice(totalprice)}",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                )

            }

        }
    }

}

@Composable
fun NoDataAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.no_data)
    )
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun CartItemList(
    dataCartInfo: List<UserCartInfo>,
    data: List<ProductResponse>,
    isChangingNumber: Pair<String, Boolean>,
    OnAddItemClicked: (String) -> Unit,
    OnRemoveItemClicked: (String) -> Unit,
    OnItemClicked: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        items(dataCartInfo.size) { index ->
            val cartItem = dataCartInfo[index]
            val productId = cartItem.product.id
            val product = data.find { it.id == productId } // Find the corresponding product
            if (product != null) {
                CartItem(
                    dataCartInfo = cartItem,
                    data = product,
                    isChangingNumber = isChangingNumber,
                    OnAddItemClicked = OnAddItemClicked,
                    OnRemoveItemClicked = OnRemoveItemClicked,
                    OnItemClicked = OnItemClicked
                )
            }
        }

    }

}

@Composable
fun CartItem(
    dataCartInfo: UserCartInfo,
    data: ProductResponse,
    isChangingNumber: Pair<String, Boolean>,
    OnAddItemClicked: (String) -> Unit,
    OnRemoveItemClicked: (String) -> Unit,
    OnItemClicked: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
//            .fillMaxWidth(fraction = 0.95f)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { OnItemClicked.invoke(data.id) },
        elevation = 4.dp,
        shape = Shapes.large,

        ) {
        Column {

            AsyncImage(
                model = BASE_URL + data.images.firstOrNull()?.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = data.title,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "From ${data.category_title} Group",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top = 18.dp),
                        text = "Product Authenticity Guarantee",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "Available in Stock to Ship",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                    Surface(
                        modifier = Modifier
                            .padding(top = 18.dp, bottom = 6.dp)
                            .clip(Shapes.large),
                        color = priceBackground

                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
                            text = stylePrice(
                                (data.unit_price.toInt() * dataCartInfo.quantity!!).toString()
                            ),
                            style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        )
                    }

                }
                Surface(
                    modifier = Modifier
                        .padding(bottom = 14.dp, end = 8.dp)
                        .align(Alignment.Bottom)
                ) {
                    Card(
                        border = BorderStroke(2.dp, Blue),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            Add Button Trash or -
                            if (dataCartInfo.quantity == 1) {
                                IconButton(onClick = { OnRemoveItemClicked.invoke(dataCartInfo.id.toString()) }) {
                                    Icon(
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            } else {
                                IconButton(onClick = { OnRemoveItemClicked.invoke(dataCartInfo.id.toString()) }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_minus),
                                        contentDescription = null
                                    )

                                }
                            }

//                            Size Of Product
                            if (isChangingNumber.first == dataCartInfo.product.id.toString() && isChangingNumber.second || isChangingNumber.first == dataCartInfo.id.toString() && isChangingNumber.second) {
                                Text(
                                    text = "...",
                                    style = TextStyle(fontSize = 18.sp),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            } else {
                                Text(
                                    text = dataCartInfo.quantity.toString(),
                                    style = TextStyle(fontSize = 18.sp),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                            }
//                            Add Button +
                            IconButton(onClick = { OnAddItemClicked.invoke(dataCartInfo.product.id.toString()) }) {
                                Icon(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }

                        }
                    }
                }

            }
        }

    }

}

@Composable
fun CartToolbar(
    OnBackClicked: () -> Unit,
    OnProfileClicked: () -> Unit

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
                text = "My Cart",
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(
                modifier = Modifier.padding(end = 6.dp),
                onClick = { OnProfileClicked.invoke() },
            ) {
                Icon(Icons.Default.Person, contentDescription = null)
            }
        }
    )
}



