package com.cornucopiacruise.ccl.feature.auth.data

import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CancellationException

class FirebaseAuthRepository(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): AuthResult = try {
        val result = auth.createUserWithEmailAndPassword(email, password)
        result.user?.updateProfile(displayName = fullName)
        AuthResult.Success
    } catch (e: CancellationException) {
        throw e
    }  catch (_: FirebaseAuthUserCollisionException) {
        AuthResult.Failure(AuthError.EMAIL_ALREADY_IN_USE)
    } catch (_: FirebaseNetworkException) {
        AuthResult.Failure(AuthError.NETWORK)
    } catch (_: CancellationException) {
        AuthResult.Failure(AuthError.UNKNOWN)
    }

    override suspend fun login(
        email: String,
        password: String
    ): AuthResult = try {
        auth.signInWithEmailAndPassword(email, password)
        AuthResult.Success
    } catch (e: CancellationException) {
        throw e
    } catch (_: FirebaseAuthInvalidCredentialsException) {
        AuthResult.Failure(AuthError.INVALID_CREDENTIALS)
    } catch (_: FirebaseAuthInvalidUserException) {
        AuthResult.Failure(AuthError.INVALID_CREDENTIALS)
    } catch (_: FirebaseNetworkException) {
        AuthResult.Failure(AuthError.NETWORK)
    } catch (_: Exception) {
        AuthResult.Failure(AuthError.UNKNOWN)
    }
}