package com.example.supplementsonlineshopproject.ui.features.signIn

import android.graphics.Paint.Style
import android.graphics.drawable.shapes.Shape
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.ui.features.IntroScreen
import com.example.supplementsonlineshopproject.ui.features.signUp.SignUpViewModel
import com.example.supplementsonlineshopproject.ui.theme.*
import com.example.supplementsonlineshopproject.util.MyScreens
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.VALUE_SUCCESS
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import java.util.SimpleTimeZone


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    MainAppTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            SignInScreen()
        }

    }
}



@Composable
fun SignInScreen() {
    val uiController= rememberSystemUiController()
    SideEffect {
        uiController.setStatusBarColor(Blue)
    }
    val context= LocalContext.current
    val navigation= getNavController()
    val viewModel= getNavViewModel<SignInViewModel>()
    clearInputs(viewModel)
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
                .fillMaxHeight(0.80f)
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            AppIcon()
            MainCardView(navigation,viewModel) {

                viewModel.signInUser(context) { result ->
                    if (result == VALUE_SUCCESS) {
                        navigation.navigate(MyScreens.MainScreen.route) {
                            popUpTo(MyScreens.IntroScreen.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    }
                }

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
fun MainCardView(navigation:NavController,viewModel:SignInViewModel,SignInEvent:()->Unit){
    val email=viewModel.email.observeAsState("")
    val password=viewModel.password.observeAsState("")
    val context= LocalContext.current
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
                text = "Sign In",
                style = TextStyle(color = Blue, fontSize = 28.sp, fontWeight = FontWeight.Bold),
            )

            MainTextField(email.value,R.drawable.ic_email,"Email"){viewModel.email.value=it}
            PasswordTextField(password.value,R.drawable.ic_password,"Password"){viewModel.password.value=it}

            Button(
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        if (password.value.length > 8) {
                            if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                                if (NetworkChecker(context).isInternetConnected){
                                    SignInEvent.invoke()
                                }else{
                                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
                                }

                            } else {
                                Toast.makeText(context,"Email Format is Incorrect",Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context,"Password Length Must be More Than 8 Char",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Fill Up Entry Data Field", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Log In"
                )

            }
            Column(
                modifier = Modifier.padding(bottom = 18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don't Have an Account?")
                    Spacer(modifier = Modifier.width(2.dp))
                    TextButton(onClick = {
                        navigation.navigate(MyScreens.SignUpScreen.route) {
                            popUpTo(MyScreens.SignInScreen.route) {
                                inclusive = true

                            }

                        }

                    }) { Text(text = "Register Here", color = Blue) }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(text = "Forgot Password?")
                    Spacer(modifier = Modifier.width(2.dp))
                    TextButton(onClick ={

                        navigation.navigate(MyScreens.ResetPasswordScreen.route){
                            popUpTo(MyScreens.SignInScreen.route) {
                                inclusive=true

                            }

                        }

                    }) {Text(text = "Click Here", color = Blue)}
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

@Composable
fun PasswordTextField(edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit) {
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), null) },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {

            val image = if (passwordVisible.value) painterResource(R.drawable.ic_visible)
            else painterResource(R.drawable.ic_invisible)

            Icon(
                painter = image,
                contentDescription = null,
                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value }
            )

        }
    )

}

fun clearInputs(viewModel: SignInViewModel){
    viewModel.email.value=""
    viewModel.password.value=""

}