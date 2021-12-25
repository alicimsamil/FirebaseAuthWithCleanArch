package com.alicimsamil.firebaseauthwithcleanarch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alicimsamil.firebaseauthwithcleanarch.R
import com.alicimsamil.firebaseauthwithcleanarch.presentation.login.LoginScreen
import com.alicimsamil.firebaseauthwithcleanarch.presentation.main.MainScreen
import com.alicimsamil.firebaseauthwithcleanarch.presentation.ui.theme.FirebaseAuthWithCleanArchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAuthWithCleanArchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation(){
    val context= LocalContext.current
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = context.getString(R.string.login_screen)){
        composable(context.getString(R.string.login_screen)) {
            LoginScreen(navController = navController)
        }
        composable(context.getString(R.string.main_screen)){
            MainScreen()
        }
    }


}


