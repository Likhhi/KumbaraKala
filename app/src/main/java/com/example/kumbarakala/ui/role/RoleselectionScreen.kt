package com.example.kumbarakala.ui.role

import android.content.Context

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import com.example.kumbarakala.R

import com.google.firebase.auth.FirebaseAuth

@Composable
fun RoleSelectionScreen(

    navController: NavController,

    onRoleSelected: (Boolean) -> Unit

) {

    val context = LocalContext.current

    /*
        SHARED PREFERENCES
     */

    val sharedPreferences =
        context.getSharedPreferences(
            "KumbaraKalaPrefs",
            Context.MODE_PRIVATE
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3E7DA))
            .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        /*
            TOP IMAGE
         */

        Image(
            painter =
                painterResource(id = R.drawable.p),

            contentDescription = "Pottery Image",

            modifier = Modifier
                .size(185.dp)
                .clip(CircleShape),

            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(32.dp))

        /*
            TITLE
         */

        Text(
            text = "Select your journey",

            fontSize = 26.sp,

            fontWeight = FontWeight.Bold,

            color = Color(0xFF5D4037),

            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(8.dp))

        /*
            SUBTITLE
         */

        Text(
            text =
                "Choose how you want to experience handcrafted heritage.",

            fontSize = 14.sp,

            color = Color(0xFF7B5E57),

            lineHeight = 24.sp,

            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(24.dp))

        /*
            ARTISAN CARD
         */

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)

                .clickable {

                    /*
                        SAVE ROLE
                     */

                    sharedPreferences
                        .edit()
                        .putString(
                            "USER_ROLE",
                            "artisan"
                        )
                        .commit()


                    /*
                        UPDATE ROLE STATE
                     */

                    onRoleSelected(true)

                    /*
                        CHECK LOGIN
                     */
                    val currentUser =
                    FirebaseAuth
                        .getInstance()
                        .currentUser
                    /*
                        NAVIGATION
                     */

                    if (currentUser != null) {

                        navController.navigate("catalog") {

                            popUpTo("role_selection") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }

                    } else {

                        navController.navigate("login") {

                            popUpTo("role_selection") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                },

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFBF8)
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 14.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Person,

                    contentDescription = "Artisan",

                    tint = Color(0xFF6D4c41),

                    modifier = Modifier.size(34.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column {

                    Text(
                        text = "Artisan",

                        fontSize = 28.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text =
                            "I create and sell handcrafted pottery",

                        fontSize = 14.sp,

                        color = Color(0xFF6D4C41)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        /*
            CUSTOMER CARD
         */

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)

                .clickable {

                    /*
                        SAVE ROLE
                     */

                    sharedPreferences
                        .edit()
                        .putString(
                            "USER_ROLE",
                            "customer"
                        )
                        .commit()

                    /*
                        UPDATE ROLE STATE
                     */

                    onRoleSelected(false)

                    /*
                        NAVIGATION
                     */

                    navController.navigate("catalog") {

                        popUpTo("role_selection") {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFBF8)
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 14.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Star,

                    contentDescription = "Customer",

                    tint = Color(0xFF6D4c41),

                    modifier = Modifier.size(34.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column {

                    Text(
                        text = "Customer",

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text =
                            "I want to discover and support authentic craft",

                        fontSize = 14.sp,

                        color = Color(0xFF6D4C41)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        /*
            FOOTER
         */

        Text(
            text = "Preserving culture through clay and storytelling",

            fontSize = 13.sp,

            color = Color(0xFF8D6E63),

            fontFamily = FontFamily.Serif,

            letterSpacing = 0.5.sp
        )
    }
}