package com.example.supplementsonlineshopproject.di




import com.example.supplementsonlineshopproject.ui.features.resetPassword.ResetPasswordViewModel
import com.example.supplementsonlineshopproject.ui.features.signIn.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.supplementsonlineshopproject.ui.features.signUp.SignUpViewModel
import com.example.supplementsonlineshopproject.util.MyScreens
import org.koin.dsl.module

val myModules= module {

    viewModel{SignUpViewModel()}
    viewModel{SignInViewModel()}
    viewModel{ResetPasswordViewModel()}

}