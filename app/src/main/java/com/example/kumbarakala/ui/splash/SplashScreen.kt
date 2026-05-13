package com.example.kumbarakala.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.animation.core.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import com.example.kumbarakala.R

import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    var progress by remember {
        mutableFloatStateOf(0f)
    }
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val imageScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    LaunchedEffect(Unit) {

        while (progress < 1f) {

            delay(30)

            progress += 0.01f
        }

        navController.navigate("role_selection") {

            popUpTo("splash") {
                inclusive = true
            }
        }
    }
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(1200))
    ) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3E7DA))
            .padding(horizontal = 24.dp, vertical = 12.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.pottery_logo),
            contentDescription = "Logo",

            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(
                    scaleX = imageScale,
                    scaleY = imageScale
                ),

            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Kumbara Kala",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723),
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Stories Crafted in Clay",
            fontSize = 18.sp,
            color = Color(0xFF6D4C41),
            fontFamily = FontFamily.Serif,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(42.dp))

        Text(
            text = "Crafting artisan stories...",
            fontSize = 20.sp,
            color = Color(0xFF4E342E),
            fontFamily = FontFamily.Serif,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        LinearProgressIndicator(
            progress = { progress },

            modifier = Modifier
                .width(180.dp)
                .height(10.dp),

            color = Color(0xFF5D4037),

            trackColor = Color(0xFFE8DDD3),
        )
    }
  }
}