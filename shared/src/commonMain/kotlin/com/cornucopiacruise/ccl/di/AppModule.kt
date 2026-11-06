package com.cornucopiacruise.ccl.di

import com.cornucopiacruise.ccl.feature.auth.data.FirebaseAuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.LoginUseCase
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.RegisterUseCase
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginViewModel
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> { FirebaseAuthRepository(Firebase.auth) }

    factoryOf(::RegisterUseCase)
    factoryOf(::LoginUseCase)

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}