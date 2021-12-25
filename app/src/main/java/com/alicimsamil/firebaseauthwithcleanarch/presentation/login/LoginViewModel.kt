package com.alicimsamil.firebaseauthwithcleanarch.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alicimsamil.firebaseauthwithcleanarch.common.AuthenticationState
import com.alicimsamil.firebaseauthwithcleanarch.common.FirebaseAuthenticationResult
import com.alicimsamil.firebaseauthwithcleanarch.domain.usecase.FirebaseSocialUseCase
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseSocialUseCase: FirebaseSocialUseCase) :
    ViewModel() {

    var authState = mutableStateOf<FirebaseAuthenticationResult<AuthenticationState>?>(null)
        private set


    fun loginWithCredential(authCredential: AuthCredential) {
        viewModelScope.launch {
            firebaseSocialUseCase.invoke(authCredential).collect {

                authState.value = it

            }
        }
    }

}