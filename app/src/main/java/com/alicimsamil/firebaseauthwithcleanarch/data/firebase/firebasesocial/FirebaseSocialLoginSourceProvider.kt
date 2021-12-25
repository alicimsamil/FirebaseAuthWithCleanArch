package com.alicimsamil.firebaseauthwithcleanarch.data.firebase.firebasesocial

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseSocialLoginSourceProvider @Inject constructor(private val firebaseAuth: FirebaseAuth) : FirebaseSocialLoginSource{
    override suspend fun loginWithCredential(authCredential: AuthCredential): FirebaseUser? {
        firebaseAuth.signInWithCredential(authCredential).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("","")
    }

}