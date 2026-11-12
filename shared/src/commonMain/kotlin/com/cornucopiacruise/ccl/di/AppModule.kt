package com.cornucopiacruise.ccl.di

import com.cornucopiacruise.ccl.feature.app.data.AppRepositoryImpl
import com.cornucopiacruise.ccl.feature.app.domain.repository.AppRepository
import com.cornucopiacruise.ccl.feature.app.domain.usecase.ObserveUserUseCase
import com.cornucopiacruise.ccl.feature.app.presentation.AppViewModel
import com.cornucopiacruise.ccl.feature.auth.data.AuthRepositoryImpl
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.LoginUseCase
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.RegisterUseCase
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginViewModel
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth> { Firebase.auth }

    single<AppRepository> { AppRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    factoryOf(::ObserveUserUseCase)
    factoryOf(::RegisterUseCase)
    factoryOf(::LoginUseCase)

    viewModelOf(::AppViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}