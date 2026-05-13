package com.example.kumbarakala.ui.profile

import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Logout

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val auth =
        FirebaseAuth.getInstance()

    val firestore =
        FirebaseFirestore.getInstance()

    val currentUser =
        auth.currentUser

    /*
        STATES
     */

    var name by remember {
        mutableStateOf("")
    }

    var artisanRole by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf("")
    }

    var quote by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    /*
        FETCH USER DATA
     */

    LaunchedEffect(Unit) {

        currentUser?.uid?.let { uid ->

            firestore
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    Toast.makeText(
                        context,
                        document.data.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    if (document.exists()) {

                        name =
                            document.getString("name")
                                ?: ""

                        artisanRole =
                            document.getString("artisanRole")
                                ?: ""

                        location =
                            document.getString("location")
                                ?: ""

                        quote =
                            document.getString("quote")
                                ?: ""
                    }

                    isLoading = false
                }
                .addOnFailureListener {

                    isLoading = false
                }
        }
    }

    /*
        UI
     */

    if (isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator()
        }

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF8F4))
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            /*
                PROFILE IMAGE
             */

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8D5C4)),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Person,

                    contentDescription = null,

                    tint = Color.White,

                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            /*
                NAME
             */

            OutlinedTextField(
                value = name,

                onValueChange = {
                    name = it
                },

                label = {
                    Text("Name")
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Person,
                        null
                    )
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            /*
                ROLE
             */

            OutlinedTextField(
                value = artisanRole,

                onValueChange = {
                    artisanRole = it
                },

                label = {
                    Text("Artisan Role")
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Work,
                        null
                    )
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            /*
                LOCATION
             */

            OutlinedTextField(
                value = location,

                onValueChange = {
                    location = it
                },

                label = {
                    Text("Location")
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.LocationOn,
                        null
                    )
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            /*
                QUOTE
             */

            OutlinedTextField(
                value = quote,

                onValueChange = {
                    quote = it
                },

                label = {
                    Text("Quote")
                },

                minLines = 4,

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            /*
                SAVE CHANGES
             */

            Button(
                onClick = {

                    currentUser?.uid?.let { uid ->

                        val updatedData =
                            hashMapOf(

                                "name" to name,

                                "artisanRole" to artisanRole,

                                "location" to location,

                                "quote" to quote
                            )

                        firestore
                            .collection("users")
                            .document(uid)
                            .update(updatedData as Map<String, Any>)
                            .addOnSuccessListener {

                                Toast.makeText(
                                    context,
                                    "Profile Updated",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {

                                Toast.makeText(
                                    context,
                                    "Update Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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

                Icon(
                    Icons.Default.Save,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Save Changes",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            /*
                LOGOUT
             */

            OutlinedButton(
                onClick = {

                    auth.signOut()

                    navController.navigate("login") {

                        popUpTo(0)

                        launchSingleTop = true
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {

                Icon(
                    Icons.Default.Logout,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Logout",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}