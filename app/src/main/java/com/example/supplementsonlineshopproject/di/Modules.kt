package com.example.supplementsonlineshopproject.di




import android.content.Context
import com.example.supplementsonlineshopproject.model.net.CreateApiService
import com.example.supplementsonlineshopproject.model.repository.user.UserRepository
import com.example.supplementsonlineshopproject.model.repository.user.UserRepositoryImpl
import com.example.supplementsonlineshopproject.ui.features.resetPassword.ResetPasswordViewModel
import com.example.supplementsonlineshopproject.ui.features.signIn.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.supplementsonlineshopproject.ui.features.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val myModules= module {

    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }
    single { CreateApiService() }
    single<UserRepository>{UserRepositoryImpl(get(),get())}

    viewModel{SignUpViewModel(get())}
    viewModel{SignInViewModel(get())}
    viewModel{ResetPasswordViewModel()}

}