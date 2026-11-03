package com.cornucopiacruise.ccl.feature.auth.presentation.login

import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.invalid_credentials
import cclshift.shared.generated.resources.network_error
import cclshift.shared.generated.resources.unknown_error
import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginError
import org.jetbrains.compose.resources.StringResource

fun LoginError.messageRes(): StringResource {
    return when (this) {
        LoginError.INVALID_CREDENTIALS -> Res.string.invalid_credentials
        LoginError.NETWORK -> Res.string.network_error
        LoginError.UNKNOWN -> Res.string.unknown_error
    }
}