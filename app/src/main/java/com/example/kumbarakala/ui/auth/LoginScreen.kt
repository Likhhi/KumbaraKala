package com.example.kumbarakala.ui.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kumbarakala.R
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Profile",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4E342E),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.pottery_banner),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {

                Text(
                    text = "Establish Your",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Identity",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Share your journey and connect with\ncollectors through your unique narrative.",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {

            Text(
                text = "Email",
                fontSize = 20.sp,
                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                placeholder = {
                    Text("Enter your email")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Password",
                fontSize = 20.sp,
                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                placeholder = {
                    Text("Enter your password")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {

                    /*
                        VALIDATION
                     */

                    if (email.isBlank() || password.isBlank()) {

                        Toast.makeText(
                            context,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    /*
                        FIREBASE LOGIN
                     */

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {

                                Toast.makeText(
                                    context,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                /*
                                    NAVIGATE TO SHARED CATALOG
                                 */

                                navController.navigate("catalog") {

                                    popUpTo("login") {
                                        inclusive = true
                                    }
                                }

                            } else {

                                Toast.makeText(
                                    context,
                                    it.exception?.message ?: "Login Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6D3D2A)
                )
            ) {

                Text(
                    text = "Login",
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Don't have an account? Register",
                color = Color(0xFF6D3D2A),
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        navController.navigate("register")
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}