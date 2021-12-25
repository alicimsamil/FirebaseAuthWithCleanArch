package com.alicimsamil.firebaseauthwithcleanarch.data.repository

import com.alicimsamil.firebaseauthwithcleanarch.data.firebase.FirebaseAuthLoginSourceProvider
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseSocialLoginSourceProvider: FirebaseAuthLoginSourceProvider) {

    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)

}