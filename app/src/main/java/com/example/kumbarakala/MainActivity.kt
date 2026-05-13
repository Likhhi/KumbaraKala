package com.example.kumbarakala
import com.example.kumbarakala.utils.StoryCardCloudinaryManager
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.rememberNavController

import com.example.kumbarakala.navigation.AppNavigation
import com.example.kumbarakala.ui.theme.KumbaraKalaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StoryCardCloudinaryManager.init(this)
            KumbaraKalaTheme {

                val navController = rememberNavController()

                AppNavigation(navController = navController)

            }
        }
    }
}
