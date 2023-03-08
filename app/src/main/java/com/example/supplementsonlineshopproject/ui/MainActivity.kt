package com.example.supplementsonlineshopproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.supplementsonlineshopproject.di.myModules
import com.example.supplementsonlineshopproject.ui.features.IntroScreen
import com.example.supplementsonlineshopproject.ui.features.resetPassword.RestPasswordScreen
import com.example.supplementsonlineshopproject.ui.features.signIn.SignInScreen
import com.example.supplementsonlineshopproject.ui.features.signUp.SignUpScreen
import com.example.supplementsonlineshopproject.ui.theme.BackgroundMain
import com.example.supplementsonlineshopproject.ui.theme.MainAppTheme
import com.example.supplementsonlineshopproject.util.KEY_CATEGORY_ARG
import com.example.supplementsonlineshopproject.util.KEY_PRODUCT_ARG
import com.example.supplementsonlineshopproject.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Koin(appDeclaration = {modules(myModules)}) {

                MainAppTheme {
                    Surface(
                        color= BackgroundMain,
                        modifier = Modifier.fillMaxSize()
                    ){
                        SupplementsUi()
                    }

                }

            }

        }
    }
}

@Composable
fun SupplementsUi(){

    val navController= rememberNavController()
    KoinNavHost(
        navController=navController,
        startDestination = MyScreens.IntroScreen.route,
    ){
        composable(MyScreens.MainScreen.route){
            MainScreen()
        }
        composable(
            route = MyScreens.ProductScreen.route+"/"+ KEY_PRODUCT_ARG,
            arguments = listOf(navArgument(KEY_PRODUCT_ARG){
                type= NavType.IntType
            })
        ){

            ProductScreen(it.arguments!!.getInt(KEY_PRODUCT_ARG,-1)) //id Product
        }


        composable(route = MyScreens.CategoryScreen.route+"/"+ KEY_CATEGORY_ARG,
        arguments = listOf(navArgument(KEY_CATEGORY_ARG){
            type= NavType.StringType
        })
        ){
            CategoryScreen(it.arguments!!.getString(KEY_CATEGORY_ARG,""))
        }




        composable(MyScreens.ProfileScreen.route){
            ProfileScreen()
        }

        composable(MyScreens.CartScreen.route){
            CartScreen()
        }
        composable(MyScreens.SignUpScreen.route){
            SignUpScreen()
        }
        composable(MyScreens.SignInScreen.route){
           SignInScreen()
        }
        composable(MyScreens.IntroScreen.route){
            IntroScreen()
        }
        composable(MyScreens.ResetPasswordScreen.route){
            RestPasswordScreen()
        }

    }

}



@Composable
fun CartScreen() {

}

@Composable
fun ProfileScreen() {

}

@Composable
fun CategoryScreen(CategoryName:String) {

}

@Composable
fun ProductScreen(ProductId:Int) {

}

@Composable
fun MainScreen() {

}


