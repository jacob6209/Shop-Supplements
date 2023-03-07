package com.example.supplementsonlineshopproject.model




import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.supplementsonlineshopproject.ui.features.signUp.SignUpViewModel
import com.example.supplementsonlineshopproject.util.MyScreens
import org.koin.dsl.module

val myModules= module {

viewModel{SignUpViewModel()}

}