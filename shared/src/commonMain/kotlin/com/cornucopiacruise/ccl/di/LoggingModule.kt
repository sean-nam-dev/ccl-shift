package com.cornucopiacruise.ccl.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.cornucopiacruise.ccl.core.log.AppConfig
import org.koin.dsl.module

val loggingModule = module {
    single<Logger> {
        val config = get<AppConfig>()
        Logger(
            config = loggerConfigInit(
                platformLogWriter(),
                minSeverity = if (config.isDebug) Severity.Verbose else Severity.Warn
            ),
            tag = "CCLShift"
        )
    }
}