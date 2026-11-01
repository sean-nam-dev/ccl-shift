package com.cornucopiacruise.ccl.feature.auth.presentation.register

import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.email_already_in_use
import cclshift.shared.generated.resources.network_error
import cclshift.shared.generated.resources.unknown_error
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterError.EMAIL_ALREADY_IN_USE
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterError.NETWORK
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterError.UNKNOWN
import org.jetbrains.compose.resources.StringResource

fun RegisterError.messageRes(): StringResource {
    return when (this) {
        EMAIL_ALREADY_IN_USE -> Res.string.email_already_in_use
        NETWORK -> Res.string.network_error
        UNKNOWN -> Res.string.unknown_error
    }
}