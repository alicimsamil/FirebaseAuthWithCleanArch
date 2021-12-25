package com.alicimsamil.firebaseauthwithcleanarch.data.firebase.firebasesocial

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface FirebaseSocialLoginSource {

    suspend fun loginWithCredential(authCredential: AuthCredential) : FirebaseUser?

}