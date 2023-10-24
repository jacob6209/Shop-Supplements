package com.example.supplementsonlineshopproject.di




import android.content.Context
import androidx.room.Room
import com.example.supplementsonlineshopproject.model.db.AppDatabase
import com.example.supplementsonlineshopproject.model.net.CreateApiService
import com.example.supplementsonlineshopproject.model.repository.cart.CartRepository
import com.example.supplementsonlineshopproject.model.repository.cart.CartRepositoryImpl
import com.example.supplementsonlineshopproject.model.repository.comment.CommentRepository
import com.example.supplementsonlineshopproject.model.repository.comment.CommntRepositoryImpl
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepository
import com.example.supplementsonlineshopproject.model.repository.product.ProductRepositoryImpl
import com.example.supplementsonlineshopproject.model.repository.user.UserRepository
import com.example.supplementsonlineshopproject.model.repository.user.UserRepositoryImpl
import com.example.supplementsonlineshopproject.ui.features.category.CategoryViewModel
import com.example.supplementsonlineshopproject.ui.features.main.MainViewModel
import com.example.supplementsonlineshopproject.ui.features.product.ProductViewModel
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
    single { Room.databaseBuilder(androidContext(),AppDatabase::class.java,"app_dataBase.db").build()}
    single<ProductRepository>{ProductRepositoryImpl(get(),get<AppDatabase>().productDao())}
    single<CommentRepository>{CommntRepositoryImpl(get())}
    single<CartRepository>{CartRepositoryImpl(get(),get())}

    viewModel{SignUpViewModel(get())}
    viewModel{SignInViewModel(get())}
    viewModel{ResetPasswordViewModel()}
    viewModel{ CategoryViewModel(get())}
    viewModel{(isNetConnected:Boolean)->MainViewModel(get(),isNetConnected)}
    viewModel{ProductViewModel(get(),get(),get())}
}