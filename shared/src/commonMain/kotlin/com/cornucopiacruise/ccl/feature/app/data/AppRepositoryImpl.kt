package com.cornucopiacruise.ccl.feature.app.data

import com.cornucopiacruise.ccl.feature.app.domain.User
import com.cornucopiacruise.ccl.feature.app.domain.repository.AppRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepositoryImpl(
    private val firebase: FirebaseAuth
) : AppRepository {
    override fun observeUser(): Flow<User?> =
        firebase.authStateChanged.map { user -> user?.let { User(it.uid) } }
}