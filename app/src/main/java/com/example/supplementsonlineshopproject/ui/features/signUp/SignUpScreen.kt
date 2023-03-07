//package com.example.supplementsonlineshopproject.ui.features.signUp
//
//import android.util.Patterns
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.ContentAlpha.medium
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.SideEffect
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.supplementsonlineshopproject.R
//import com.example.supplementsonlineshopproject.ui.theme.BackgroundMain
//import com.example.supplementsonlineshopproject.ui.theme.Blue
//import com.example.supplementsonlineshopproject.ui.theme.MainAppTheme
//import com.example.supplementsonlineshopproject.ui.theme.Shapes
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import dev.burnoo.cokoin.navigation.getNavController
//import dev.burnoo.cokoin.navigation.getNavViewModel
//
//
//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview() {
//
//    MainAppTheme {
//        Surface(
//            color = BackgroundMain,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            SignUpScreen()
//        }
//    }
//
//}
//
//@Composable
//fun SignUpScreen() {
//    val uiController = rememberSystemUiController()
//    SideEffect { uiController.setStatusBarColor(Blue) }
//
//    val navigation = getNavController()
//
//    Box {
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.4f)
//                .background(Blue)
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.95f),
//            verticalArrangement = Arrangement.SpaceEvenly,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            IconApp()
//
//            MainCardView() {
//
//            }
//
//        }
//    }
//}
//
//@Composable
//fun IconApp() {
//
//    Surface(
//        modifier = Modifier
//            .clip(CircleShape)
//            .size(64.dp)
//    ) {
//
//        Image(
//            modifier = Modifier.padding(14.dp),
//            painter = painterResource(id = R.drawable.ic_icon_app),
//            contentDescription = null
//        )
//
//    }
//
//}
//
//@Composable
//fun MainCardView(SignUpEvent: () -> Unit) {
//    val name = remember { mutableStateOf("") }
//    val email = remember { mutableStateOf("") }
//    val password = remember { mutableStateOf("") }
//    val confirmpassword = remember { mutableStateOf("") }
//
//
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        elevation = 10.dp,
//        shape = Shapes.medium
//    ) {
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Text(
//                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
//                text = "Sign Up",
//                style = TextStyle(color = Blue, fontSize = 28.sp, fontWeight = FontWeight.Bold)
//            )
//
//            MainTextField(name.value, R.drawable.ic_person, "Your Full Name") { name.value = it }
//            MainTextField(email.value, R.drawable.ic_email, "Email") { email.value = it }
//            MainTextField(password.value, R.drawable.ic_password, "Password") {
//                password.value = it
//            }
//            MainTextField(
//                confirmpassword.value,
//                R.drawable.ic_password,
//                "Confirm Password"
//            ) { confirmpassword.value = it }
//            Button(onClick = {
//            }, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp)) {
//                Text(
//                    modifier = Modifier.padding(8.dp),
//                    text = "Register Account"
//                )
//            }
//
//            Row(
//                modifier = Modifier.padding(bottom = 18.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Text("Already have  an account?")
//                Spacer(modifier = Modifier.width(8.dp))
//
//                TextButton(onClick = {}
//
//                ) { Text("Log In", color = Blue) }
//
//            }
//
//
//        }
//
//    }
//
//
//}
//
//@Composable
//fun MainTextField(edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit) {
//
//    OutlinedTextField(
//        label = { Text(hint) },
//        value = edtValue,
//        singleLine = true,
//        onValueChange = onValueChanges,
//        placeholder = { Text(hint) },
//        modifier = Modifier
//            .fillMaxWidth(0.9f)
//            .padding(top = 12.dp),
//        shape = Shapes.medium,
//        leadingIcon = { Icon(painterResource(icon), null) }
//    )
//
//}
//
//@Composable
//fun PasswordTextField(edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit) {
//    val passwordVisible = remember { mutableStateOf(false) }
//
//    OutlinedTextField(
//        label = { Text(hint) },
//        value = edtValue,
//        singleLine = true,
//        onValueChange = onValueChanges,
//        placeholder = { Text(hint) },
//        modifier = Modifier
//            .fillMaxWidth(0.9f)
//            .padding(top = 12.dp),
//        shape = Shapes.medium,
//        leadingIcon = { Icon(painterResource(icon), null) },
//        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        trailingIcon = {
//
//            val image = if (passwordVisible.value) painterResource(R.drawable.ic_invisible)
//            else painterResource(R.drawable.ic_visible)
//
//            Icon(
//                painter = image,
//                contentDescription = null,
//                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value }
//            )
//
//        }
//    )
//
//}


//------------------------------------------------------------------------------


package com.example.supplementsonlineshopproject.ui.features.signUp

import android.graphics.Paint.Style
import android.graphics.drawable.shapes.Shape
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.ui.features.IntroScreen
import com.example.supplementsonlineshopproject.ui.theme.*
import com.example.supplementsonlineshopproject.util.MyScreens
import java.util.SimpleTimeZone


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {


    MainAppTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            SignUpScreen()
        }

    }
}



@Composable
fun SignUpScreen() {

    Box{


        Box (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Blue)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            AppIcon()
            MainCardView() {

            }

        }


    }

}


@Composable
fun AppIcon(){
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .size(150.dp)
    ) {
        Image(
            modifier = Modifier.padding(14.dp),
            painter = painterResource(id = R.drawable.ic_app) ,
            contentDescription =null
        )
    }

}




@Composable
fun MainCardView(SignUpEvent:()->Unit){
    val name=remember{ mutableStateOf("") }
    val email=remember{ mutableStateOf("") }
    val password=remember{ mutableStateOf("") }
    val confirmpassword=remember{ mutableStateOf("") }


// Old Method
//    Card(modifier = Modifier.fillMaxWidth(.9f))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = 10.dp,
        shape = Shapes.medium

    )

     {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(
                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                text = "Sign Up",
                style = TextStyle(color = Blue, fontSize = 28.sp, fontWeight = FontWeight.Bold),
            )

            MainTextField(name.value,R.drawable.ic_person,"Your Full Name"){name.value=it}
            MainTextField(email.value,R.drawable.ic_email,"Email"){email.value=it}
            MainTextField(password.value,R.drawable.ic_password,"Password"){password.value=it}
            MainTextField(confirmpassword.value,R.drawable.ic_password,"Confirm Password"){confirmpassword.value=it}


            Button(
                onClick = SignUpEvent,
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Register Account"
                )

            }

            Row(
                modifier = Modifier.padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Already have an Account?")
                Spacer(modifier = Modifier.width(8.dp))
               TextButton(onClick ={}) {
                   Text(text = "Log In", color = Blue)

               }
            }


        }
    }

}



@Composable
fun MainTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        label = {Text(hint)},
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChange,
        placeholder = {Text(hint)},
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), contentDescription = null)},
    )
}