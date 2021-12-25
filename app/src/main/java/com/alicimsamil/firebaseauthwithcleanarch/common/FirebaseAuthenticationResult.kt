package com.alicimsamil.firebaseauthwithcleanarch.common

import com.google.firebase.auth.FirebaseAuthException

sealed class FirebaseAuthenticationResult<AuthenticationState>(
    val authenticationState: AuthenticationState,
    val firebaseException: FirebaseAuthException? = null
) {

    class Success<AuthenticationState>(
        authenticationState: AuthenticationState,
    ) : FirebaseAuthenticationResult<AuthenticationState>(authenticationState)

    class Failure<AuthenticationState>(
        authenticationState: AuthenticationState,
        firebaseException: FirebaseAuthException?
    ) : FirebaseAuthenticationResult<AuthenticationState>(
        authenticationState,
        firebaseException
    )

    class InProgress<AuthenticationState>(
        authenticationState: AuthenticationState,
    ) : FirebaseAuthenticationResult<AuthenticationState>(authenticationState)
}
