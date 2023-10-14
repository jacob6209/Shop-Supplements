package com.example.supplementsonlineshopproject.ui.features.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.supplementsonlineshopproject.model.data.ProductResponse
import com.example.supplementsonlineshopproject.ui.theme.Blue
import com.example.supplementsonlineshopproject.ui.theme.Shapes
import com.example.supplementsonlineshopproject.util.BASE_URL
import com.example.supplementsonlineshopproject.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun CategoryScreen(categoryTitle: String) {
    val viewModel = getNavViewModel<CategoryViewModel>()
    viewModel.loadDataByCategory(categoryTitle)

    val navigation = getNavController()
    val data = viewModel.dataProducts
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CategoryToolBar(categoryName = categoryTitle)

        CategoryList(data.value) {

            navigation.navigate(MyScreens.ProductScreen.route + "/" + it)

        }
    }
}

@Composable
fun CategoryItem(data: ProductResponse, onProductClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onProductClicked.invoke(data.id) },
        elevation = 4.dp,
        shape = Shapes.large,

        ) {
        Column {


            AsyncImage(
                model = BASE_URL+data.images.firstOrNull()?.image,
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
                        text = "${data.unit_price} Tomans",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )

                }
                Surface(
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                        .align(Alignment.Bottom)
                        .clip(Shapes.large),
                    color = Blue
                    ,
                ) {

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "${data.soled_item} Sold",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    )
                }

            }
        }

    }

}

@Composable
fun CategoryList(data: List<ProductResponse>, onProductClicked: (Int) -> Unit) {
LazyColumn(modifier = Modifier.fillMaxSize()){
    items(data.size){
        CategoryItem(data[it], onProductClicked )
    }

}
}

@Composable
fun CategoryToolBar(categoryName: String,) {
    TopAppBar(elevation = 0.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                modifier =Modifier.fillMaxWidth(),
                text = "$categoryName",
                textAlign = TextAlign.Center
            )
        }
    )

}
