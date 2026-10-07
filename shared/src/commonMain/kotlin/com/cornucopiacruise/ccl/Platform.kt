package com.cornucopiacruise.ccl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform