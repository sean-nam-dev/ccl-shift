package com.cornucopiacruise.ccl.feature.auth.data

import co.touchlab.kermit.Logger
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CancellationException

class AuthRepositoryImpl(
    private val firebase: FirebaseAuth,
    logger: Logger
) : AuthRepository {
    private val log = logger.withTag("AuthRepositoryImpl")

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): AuthResult {
        log.d { "Register action requested" }
        return try {
            log.d { "Create user started" }
            val result = firebase.createUserWithEmailAndPassword(email, password)
            result.user?.updateProfile(displayName = fullName) ?: throw Exception()
            log.i { "Success" }
            AuthResult.Success
        } catch (e: CancellationException) {
            throw e
        }  catch (e: FirebaseAuthUserCollisionException) {
            log.w(e) { "Failed with email already in use" }
            AuthResult.Failure(AuthError.EMAIL_ALREADY_IN_USE)
        } catch (e: FirebaseNetworkException) {
            log.w(e) { "Failed with network" }
            AuthResult.Failure(AuthError.NETWORK)
        } catch (e: Exception) {
            log.e(e) { "Failed with unknown" }
            AuthResult.Failure(AuthError.UNKNOWN)
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): AuthResult {
        log.d { "Login action requested" }
        return try {
            log.d { "Sign in started" }
            firebase.signInWithEmailAndPassword(email, password)
            log.i { "Success" }
            AuthResult.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            log.w(e) { "Failed with invalid credentials" }
            AuthResult.Failure(AuthError.INVALID_CREDENTIALS)
        } catch (e: FirebaseAuthInvalidUserException) {
            log.w(e) { "Failed with invalid user" }
            AuthResult.Failure(AuthError.INVALID_CREDENTIALS)
        } catch (e: FirebaseNetworkException) {
            log.w(e) { "Failed with network" }
            AuthResult.Failure(AuthError.NETWORK)
        } catch (e: Exception) {
            log.e(e) { "Failed with unknown" }
            AuthResult.Failure(AuthError.UNKNOWN)
        }
    }
}