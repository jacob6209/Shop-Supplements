package com.example.supplementsonlineshopproject.ui.features.resetPassword

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.ui.theme.*
import com.example.supplementsonlineshopproject.util.MyScreens
import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel


@Preview(showBackground = true)
@Composable
fun RestPasswordScreenPreview() {
    MainAppTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            RestPasswordScreen()
        }

    }
}



@Composable
fun  RestPasswordScreen() {
    val uiController= rememberSystemUiController()
    SideEffect {
        uiController.setStatusBarColor(Blue)
    }

    val navigation= getNavController()
    val viewModel= getNavViewModel<ResetPasswordViewModel>()
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
                .fillMaxHeight(0.75f),

            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            AppIcon()
            MainCardView(navigation,viewModel) {

                viewModel.ResetPasswordUser()


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
fun MainCardView(navigation:NavController, viewModel:ResetPasswordViewModel, ResetPasswordEvent:()->Unit){
    val email=viewModel.email.observeAsState("")
    val context= LocalContext.current
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
                text = "Reset Password",
                style = TextStyle(color = Blue, fontSize = 28.sp, fontWeight = FontWeight.Bold),
            )
            MainTextField(email.value,R.drawable.ic_email,"Email"){viewModel.email.value=it}
            Button(
                onClick = {
                    if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                        if (NetworkChecker(context).isInternetConnected){
                            ResetPasswordEvent.invoke()
                        }else{
                            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context,"Email Format Incorrect", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            )
            {
             Text(text = "Reset", color= Color.White)
            }

            Row(
                modifier = Modifier.padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Don't Have an Account?")
                Spacer(modifier = Modifier.width(2.dp))
               TextButton(onClick ={

                   navigation.navigate(MyScreens.SignUpScreen.route){
                       popUpTo(MyScreens.IntroScreen.route) {
                           inclusive=false

                       }

                   }

               }) {Text(text = "Register Here", color = Blue)}
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
