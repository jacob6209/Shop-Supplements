package com.example.supplementsonlineshopproject.ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.ui.theme.BackgroundMain
import com.example.supplementsonlineshopproject.ui.theme.MainAppTheme


@Preview(showBackground = true)
@Composable
fun InteroScreenPreview() {


    MainAppTheme {

        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxWidth(),
        )
        {
            IntroScreen()

        }


    }


}

@Composable
fun IntroScreen() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(R.drawable.img_intro),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.78f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Sign Up",
            )
            
        }

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            colors =ButtonDefaults.buttonColors(backgroundColor= Color.White),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Sign In",
                color = Color.Blue
            )

        }

    }
}

