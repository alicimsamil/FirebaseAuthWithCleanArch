package com.alicimsamil.firebaseauthwithcleanarch.presentation.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alicimsamil.firebaseauthwithcleanarch.R
import com.alicimsamil.firebaseauthwithcleanarch.common.AuthenticationState
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<LoginViewModel>()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.loginWithCredential(credential)

            } catch (e: ApiException) {
                Log.w("Error", "Google sign in failed", e)
            }

        }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = { facebookLogin(context,viewModel) }) {

                Text(text = "Facebook")

            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { googleLogin(context, launcher) }) {

                Text(text = "Google")

            }
        }
    }


    CheckLoginState(viewModel = viewModel, navController = navController, context = context )



}


private fun facebookLogin(context: Context,viewModel: LoginViewModel) {

    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()

    loginManager.logIn(
        context as ActivityResultRegistryOwner,
        callbackManager,
        listOf("email")
    )

    loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult) {
            val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
            viewModel.loginWithCredential(credential)
        }
        override fun onCancel() {
        }
        override fun onError(error: FacebookException) {
        }
    })
}


private fun googleLogin(
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    lateinit var googleSignInClient: GoogleSignInClient

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id_X))
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(context, gso)

    val signInIntent = googleSignInClient.signInIntent

    launcher.launch(signInIntent)

}


@Composable
fun CheckLoginState(viewModel: LoginViewModel,navController: NavController,context: Context) {
    val loginState = viewModel.authState
    when (loginState.value?.authenticationState) {
        AuthenticationState.AUTHENTICATED -> {
            navController.navigate(context.getString(R.string.main_screen))
        }

        AuthenticationState.UNAUTHENTICATED -> {
            println(loginState.value?.firebaseException?.localizedMessage)
        }

        AuthenticationState.IN_PROGRESS -> {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White
                    )
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

