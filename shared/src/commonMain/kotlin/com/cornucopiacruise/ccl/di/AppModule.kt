package com.cornucopiacruise.ccl.di

import com.cornucopiacruise.ccl.feature.auth.data.login.StubLoginRepository
import com.cornucopiacruise.ccl.feature.auth.data.register.StubRegisterRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterRepository
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginViewModel
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<RegisterRepository> { StubRegisterRepository() }
    single<LoginRepository> { StubLoginRepository() }

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}