package com.cornucopiacruise.ccl.feature.auth.presentation.component

import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.email_already_in_use
import cclshift.shared.generated.resources.invalid_credentials
import cclshift.shared.generated.resources.network_error
import cclshift.shared.generated.resources.unknown_error
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError.*
import org.jetbrains.compose.resources.StringResource

fun AuthError.messageRes(): StringResource {
    return when (this) {
        EMAIL_ALREADY_IN_USE -> Res.string.email_already_in_use
        INVALID_CREDENTIALS -> Res.string.invalid_credentials
        NETWORK -> Res.string.network_error
        UNKNOWN -> Res.string.unknown_error
    }
}