package com.alicimsamil.firebaseauthwithcleanarch.data.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthLoginSourceProvider @Inject constructor(private val firebaseAuthInstance: FirebaseAuth) :
    FirebaseAuthLoginSource {
    override suspend fun loginWithCredential(authCredential: AuthCredential): FirebaseUser? {
        firebaseAuthInstance.signInWithCredential(authCredential).await()
        return firebaseAuthInstance.currentUser ?: throw FirebaseAuthException("","")
    }

}