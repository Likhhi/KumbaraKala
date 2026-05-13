package com.example.kumbarakala.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kumbarakala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val auth =
        FirebaseAuth.getInstance()

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf("")
    }

    var story by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        Text(
            text = "Register",

            fontSize = 42.sp,

            fontWeight = FontWeight.Bold,

            color = Color(0xFF4E342E),

            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(top = 20.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.pottery_banner
                ),

                contentDescription = null,

                modifier = Modifier.fillMaxSize(),

                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(
                        Alignment.BottomStart
                    )
                    .padding(20.dp)
            ) {

                Text(
                    text = "Begin Your",

                    color = Color.White,

                    fontSize = 34.sp,

                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Journey",

                    color = Color.White,

                    fontSize = 34.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        "Share your artistry and connect\nwith pottery lovers everywhere.",

                    color = Color.White,

                    fontSize = 16.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {

            /*
                FULL NAME
             */

            FormLabel("Full Name")

            OutlinedTextField(
                value = name,

                onValueChange = {
                    name = it
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Person,
                        null
                    )
                },

                placeholder = {
                    Text("Enter your full name")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
                EMAIL
             */

            FormLabel("Email")

            OutlinedTextField(
                value = email,

                onValueChange = {
                    email = it
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Email,
                        null
                    )
                },

                placeholder = {
                    Text("Enter your email")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
                PHONE
             */

            FormLabel("Phone Number")

            OutlinedTextField(
                value = phone,

                onValueChange = {
                    phone = it
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Phone,
                        null
                    )
                },

                placeholder = {
                    Text("Enter your phone number")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
                LOCATION
             */

            FormLabel("Location")

            OutlinedTextField(
                value = location,

                onValueChange = {
                    location = it
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.LocationOn,
                        null
                    )
                },

                placeholder = {
                    Text("Enter your location")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
                STORY
             */

            FormLabel("Story Narration")

            OutlinedTextField(
                value = story,

                onValueChange = {
                    story = it
                },

                placeholder = {
                    Text(
                        "Tell your artisan journey..."
                    )
                },

                minLines = 4,

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
                PASSWORD
             */

            FormLabel("Password")

            OutlinedTextField(
                value = password,

                onValueChange = {
                    password = it
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Lock,
                        null
                    )
                },

                placeholder = {
                    Text("Enter your password")
                },

                visualTransformation =
                    PasswordVisualTransformation(),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            /*
                REGISTER BUTTON
             */

            Button(

                enabled = !isLoading,

                onClick = {

                    if (
                        name.isBlank() ||
                        email.isBlank() ||
                        phone.isBlank() ||
                        location.isBlank() ||
                        story.isBlank() ||
                        password.isBlank()
                    ) {

                        Toast.makeText(
                            context,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    if (
                        !isValidPassword(password)
                    ) {

                        Toast.makeText(
                            context,
                            "Password must contain uppercase, lowercase, number and special character",
                            Toast.LENGTH_LONG
                        ).show()

                        return@Button
                    }

                    isLoading = true

                    Log.d(
                        "REGISTER_DEBUG",
                        email
                    )

                    auth
                        .createUserWithEmailAndPassword(
                            email.trim(),
                            password.trim()
                        )

                        .addOnSuccessListener {

                            val user =
                                auth.currentUser

                            if (user == null) {

                                isLoading = false

                                Toast.makeText(
                                    context,
                                    "User creation failed",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@addOnSuccessListener
                            }

                            val firestore =
                                FirebaseFirestore.getInstance()

                            val artisanData =
                                hashMapOf(

                                    "name" to
                                            name.trim(),

                                    "email" to
                                            email.trim(),

                                    "phone" to
                                            phone.trim(),

                                    "location" to
                                            location.trim(),

                                    "quote" to
                                            story.trim(),

                                    "artisanRole" to
                                            "Pottery Artisan",

                                    "role" to
                                            "artisan",

                                    "createdAt" to
                                            System.currentTimeMillis()
                                )

                            firestore
                                .collection("users")
                                .document(user.uid)
                                .set(artisanData)

                                .addOnSuccessListener {

                                    isLoading = false

                                    Log.d(
                                        "FIRESTORE_SUCCESS",
                                        "User Registered Successfully"
                                    )

                                    Toast.makeText(
                                        context,
                                        "Registration Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    auth.signOut()

                                    navController.navigate("login") {

                                        popUpTo("register") {
                                            inclusive = true
                                        }
                                    }

                                    return@addOnSuccessListener
                                }

                                .addOnFailureListener { e ->

                                    isLoading = false

                                    Log.e(
                                        "FIRESTORE_ERROR",
                                        e.message.toString()
                                    )

                                    Toast.makeText(
                                        context,
                                        "Firestore Error: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        .addOnFailureListener { e ->

                            isLoading = false

                            Toast.makeText(
                                context,
                                e.message
                                    ?: "Registration Failed",

                                Toast.LENGTH_LONG
                            ).show()
                        }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF6D3D2A)
                    )
            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        color = Color.White,

                        modifier = Modifier.size(24.dp)
                    )

                } else {

                    Text(
                        text = "Register",

                        fontSize = 18.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text =
                    "Already have an account? Login",

                color = Color(0xFF6D3D2A),

                fontSize = 16.sp,

                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .clickable {

                        navController.navigate(
                            "login"
                        )
                    }
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}

fun isValidPassword(
    password: String
): Boolean {

    val passwordPattern =
        Regex(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$"
        )

    return passwordPattern.matches(password)
}

@Composable
fun FormLabel(
    text: String
) {

    Text(
        text = text,

        fontSize = 20.sp,

        color = Color(0xFF4E342E)
    )

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}