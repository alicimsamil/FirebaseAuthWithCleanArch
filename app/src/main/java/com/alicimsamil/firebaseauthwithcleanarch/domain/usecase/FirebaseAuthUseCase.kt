package com.alicimsamil.firebaseauthwithcleanarch.domain.usecase

import com.alicimsamil.firebaseauthwithcleanarch.common.AuthenticationState
import com.alicimsamil.firebaseauthwithcleanarch.common.FirebaseAuthenticationResult
import com.alicimsamil.firebaseauthwithcleanarch.data.repository.FirebaseRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseAuthUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(authCredential: AuthCredential)= flow{

        emit(FirebaseAuthenticationResult.InProgress(AuthenticationState.IN_PROGRESS))

        try {

            firebaseRepository.loginWithCredential(authCredential)?.let {

                emit(FirebaseAuthenticationResult.Success(AuthenticationState.AUTHENTICATED))

            } ?: run {

                emit(FirebaseAuthenticationResult.Failure(AuthenticationState.UNAUTHENTICATED,null))

            }



        } catch (e:FirebaseAuthException){

                emit(FirebaseAuthenticationResult.Failure(AuthenticationState.UNAUTHENTICATED,e))
        }

    }


}