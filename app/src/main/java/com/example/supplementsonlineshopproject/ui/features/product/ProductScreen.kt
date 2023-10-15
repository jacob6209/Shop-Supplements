package com.example.supplementsonlineshopproject.ui.features.product

import android.icu.text.CaseMap.Title
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.supplementsonlineshopproject.model.data.AdsResponse
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.ui.theme.BackgroundMain
import com.example.supplementsonlineshopproject.ui.theme.Blue
import com.example.supplementsonlineshopproject.ui.theme.CardViewBackgroundItem
import com.example.supplementsonlineshopproject.ui.theme.DIMENS_114dp
import com.example.supplementsonlineshopproject.ui.theme.DIMENS_12dp
import com.example.supplementsonlineshopproject.ui.theme.DIMENS_16dp
import com.example.supplementsonlineshopproject.ui.theme.MainAppTheme
import com.example.supplementsonlineshopproject.ui.theme.Shapes
import com.example.supplementsonlineshopproject.util.BASE_URL
import com.example.supplementsonlineshopproject.util.CATEGORY
import com.example.supplementsonlineshopproject.util.MyScreens
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.TAGS
import com.example.supplementsonlineshopproject.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import org.koin.core.parameter.parametersOf
import java.nio.file.WatchEvent
import kotlin.math.absoluteValue

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


@Composable
fun ProductScreen(productId: Int) {
    val context = LocalContext.current
    val uiController = rememberSystemUiController()
    val navigation = getNavController()
    val viewModel = getNavViewModel<ProductViewModel>()
    viewModel.loadData(productId)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 58.dp)
        ) {
            ProductToolbar(
                productName = "Datail",
                badgeNumber = 0,
                OnBackClicked = {navigation.popBackStack()},
                OnCartClicked = {
                    if (NetworkChecker(context).isInternetConnected){
                        navigation.navigate(MyScreens.CartScreen.route)
                    }else{
                        Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            ProductItem(viewModel.thisProduct.value!!, OnCategoryClicked = {
                navigation.navigate(MyScreens.CategoryScreen.route+"/"+it)
            }, modifier = Modifier)

        }

        AddToCart()
    }

}

@Composable
fun ProductItem(data: ProductResponse,OnCategoryClicked: (String) -> Unit,modifier: Modifier) {
    Column(
        modifier =Modifier.padding(16.dp)
    ) {
        ProductDesign(data,OnCategoryClicked,modifier)
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDesign(data:ProductResponse,OnCategoryClicked:(String)->Unit,modifier: Modifier) {
    SliderBanner(data,modifier)
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
    
    TextButton(onClick = { OnCategoryClicked.invoke(data.category_title)}) {
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
fun AddToCart() {

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
                    model = BASE_URL+data.images[page].image,
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





