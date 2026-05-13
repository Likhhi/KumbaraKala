package com.example.kumbarakala.ui.productdetails
import androidx.navigation.NavController
import com.example.kumbarakala.model.Product
import android.widget.Toast
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.AlertDialog

import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.AsyncImage

/*
-------------------------------------------------------
PRODUCT MODEL
-------------------------------------------------------
*/

data class Product(

    val productId: String = "",

    val artisanId: String = "",

    val name: String = "",

    val description: String = "",

    val benefits: List<String> = emptyList(),

    val price: String = "",

    val category: String = "",

    val imageUrl: String = "",

    val createdAt: Long = 0L
)

/*
-------------------------------------------------------
PRODUCT DETAILS SCREEN
-------------------------------------------------------
*/

@Composable

fun ProductDetailsScreen(

    navController: NavController,

    product: Product,

    isArtisan: Boolean

) {

    val context = LocalContext.current

    val firestore = FirebaseFirestore.getInstance()

    var menuExpanded by remember {
        mutableStateOf(false)
    }
    var artisanName by remember {
        mutableStateOf("")
    }

    var artisanLocation by remember {
        mutableStateOf("")
    }
    var storyExists by remember {
        mutableStateOf(false)
    }

    var checkingStory by remember {
        mutableStateOf(true)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }


    var artisanRole by remember {
        mutableStateOf("")
    }

    var artisanQuote by remember {
        mutableStateOf("")
    }
    var shortArtisanQuote by remember {
        mutableStateOf("")
    }
    val storyTitles = mapOf(

        "Water Pots" to "Earth Preserved",

        "Kitchenware" to "Crafted for Generations",

        "Clay Lamps" to "Light from Tradition",

        "Decor" to "Shapes of Heritage",

        "Pottery" to "The Vessel's Soul"
    )

    val storyHeading =

        storyTitles[product.category]

            ?: "Crafted Heritage"
    LaunchedEffect(Unit) {

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(product.artisanId)
            .get()

            .addOnSuccessListener { document ->

                artisanName =
                    document.getString("name")
                        ?: ""

                artisanLocation =
                    document.getString("location")
                        ?: ""

                artisanRole =
                    document.getString("artisanRole")
                        ?: ""

                artisanQuote =
                    document.getString("quote")
                        ?: ""

                shortArtisanQuote =
                    artisanQuote
                        .split(". ")
                        .take(2)
                        .joinToString(". ")
                        .trim() + "."
            }
        firestore
            .collection("story_cards")
            .document(product.productId)
            .get()

            .addOnSuccessListener { document ->

                storyExists = document.exists()

                checkingStory = false
            }

            .addOnFailureListener {

                storyExists = false

                checkingStory = false
            }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F3F0))
            .verticalScroll(rememberScrollState())

    ) {

        /*
        -------------------------------------------------------
        TOP BAR
        -------------------------------------------------------
        */

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 18.dp
                ),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,

                    contentDescription = null,

                    tint = Color(0xFF4E2D1F),

                    modifier = Modifier.size(32.dp)
                )
            }

            /*
            -------------------------------------------------------
            SHOW MENU ONLY FOR ARTISAN
            -------------------------------------------------------
            */

            if (isArtisan) {

                Box {

                    IconButton(
                        onClick = {
                            menuExpanded = true
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.MoreVert,

                            contentDescription = null,

                            tint = Color(0xFF4E2D1F)
                        )
                    }

                    DropdownMenu(

                        expanded = menuExpanded,

                        onDismissRequest = {
                            menuExpanded = false
                        }

                    ) {

                        DropdownMenuItem(

                            text = {

                                Row(
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Delete,

                                        contentDescription = null,

                                        tint = Color.Red
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.width(10.dp)
                                    )

                                    Text(
                                        text = "Delete Product",
                                        color = Color.Red
                                    )
                                }
                            },

                            onClick = {

                                menuExpanded = false

                                showDeleteDialog = true
                            }
                        )
                    }
                }
                if (showDeleteDialog) {

                    AlertDialog(

                        onDismissRequest = {
                            showDeleteDialog = false
                        },

                        title = {

                            Text(
                                text = "Delete Product",
                                color = Color.Black
                            )
                        },

                        text = {

                            Text(
                                "Are you sure you want to delete this product?\n\nThis action cannot be undone."
                            )
                        },

                        confirmButton = {

                            Button(

                                onClick = {

                                    firestore
                                        .collection("products")
                                        .document(product.productId)
                                        .delete()
                                        .addOnSuccessListener {

                                            Toast.makeText(
                                                context,
                                                "Product Deleted",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            showDeleteDialog = false

                                            navController.popBackStack()
                                        }
                                },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                )
                            ) {

                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null
                                )

                                Spacer(
                                    modifier = Modifier.width(6.dp)
                                )

                                Text("Delete")
                            }
                        },

                        dismissButton = {

                            OutlinedButton(
                                onClick = {
                                    showDeleteDialog = false
                                }
                            ) {

                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }

        /*
        -------------------------------------------------------
        MAIN PRODUCT IMAGE
        -------------------------------------------------------
        */

        AsyncImage(

            model = product.imageUrl,

            contentDescription = null,

            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(horizontal = 18.dp)
                .clip(RoundedCornerShape(28.dp)),

            contentScale = ContentScale.Crop
        )

        /*
        -------------------------------------------------------
        DETAILS CARD
        -------------------------------------------------------
        */

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-18).dp)
                .padding(horizontal = 14.dp),

            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            ),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFAF7F5)
            )

        ) {

            Column(

                modifier = Modifier.padding(22.dp)

            ) {

                Text(

                    text = "${product.category.uppercase()} COLLECTION",

                    fontSize = 16.sp,

                    color = Color(0xFF7A5748),

                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(

                    text = product.name,

                    fontSize = 34.sp,

                    lineHeight = 48.sp,

                    fontWeight = FontWeight.Bold,

                    fontFamily = FontFamily.Serif,

                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(

                    text = "₹${product.price}",

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF5A2D1C),

                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(18.dp))

                /*
                -------------------------------------------------------
                BENEFITS
                -------------------------------------------------------
                */

                Row(

                    modifier = Modifier
                        .horizontalScroll(
                            rememberScrollState()
                        ),

                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)

                ) {

                    product.benefits.forEach { benefit ->

                        BenefitChip(benefit)
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                /*
                -------------------------------------------------------
                DESCRIPTION
                -------------------------------------------------------
                */

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.Description,

                        contentDescription = null,

                        tint = Color(0xFF5A2D1C),

                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(10.dp)
                    )

                    Text(

                        text = storyHeading,

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold,

                        fontFamily = FontFamily.Serif
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(

                    text = product.description,

                    fontSize = 18.sp,

                    lineHeight = 30.sp,

                    color = Color(0xFF2F2F2F),

                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE8D9D2)
                )

                Spacer(modifier = Modifier.height(20.dp))

                /*
                -------------------------------------------------------
                ARTISAN CARD
                -------------------------------------------------------
                */

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(
                        containerColor =
                            Color(0xFFF8F0EC)
                    ),

                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color(0xFFE8D9D2)
                    )

                ) {

                    Row(

                        modifier = Modifier.padding(16.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        /*
                        ARTISAN IMAGE PLACEHOLDER
                        */

                        Box(

                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(
                                    Color(0xFFD9C2B6)
                                ),

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Text(
                                text =

                                    if (artisanName.isNotBlank())

                                        artisanName
                                            .trim()
                                            .first()
                                            .toString()
                                            .uppercase()

                                    else

                                        "A",

                                color = Color(0xFF6D4C41),

                                fontWeight = FontWeight.Bold,

                                fontSize = 28.sp
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.width(16.dp)
                        )

                        Column (modifier = Modifier.weight(1f)){

                            Text(

                                text = artisanName,

                                fontSize = 24.sp,

                                fontWeight = FontWeight.Bold,

                                fontFamily = FontFamily.Serif
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Text(

                                text = artisanRole,

                                fontSize = 18.sp,

                                color = Color.DarkGray
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.LocationOn,

                                    contentDescription = null,

                                    tint = Color(0xFF5A2D1C),

                                    modifier =
                                        Modifier.size(18.dp)
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(4.dp)
                                )

                                Text(
                                    text = artisanLocation
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            Text(

                                text =
                                    shortArtisanQuote,

                                fontStyle = FontStyle.Italic,

                                fontSize = 18.sp,

                                lineHeight = 26.sp,

                                fontFamily = FontFamily.Serif
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                /*
                -------------------------------------------------------
                STORY CARD BUTTON
                -------------------------------------------------------
                */

                if (!checkingStory) {

                    Button(

                        onClick = {

                            if (storyExists) {

                                navController.navigate(
                                    "preview_story/${product.productId}"
                                )

                            } else {

                                navController.navigate(
                                    "generate_story/${product.productId}"
                                )
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6D3D2A)
                        )
                    ) {

                        Text(

                            text =
                                if (storyExists)
                                    "View Heritage Story"
                                else
                                    "Generate Heritage Story ",

                            fontSize = 20.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(26.dp))

                /*
                -------------------------------------------------------
                MORE VIEWS
                -------------------------------------------------------
                */


            }
        }
    }
}

/*
-------------------------------------------------------
BENEFIT CHIP
-------------------------------------------------------
*/

@Composable
fun BenefitChip(
    text: String
) {

    val icon = when {

        text.contains(
            "ph",
            true
        ) -> Icons.Outlined.Science

        text.contains(
            "cool",
            true
        ) -> Icons.Outlined.WaterDrop

        else -> Icons.Outlined.Eco
    }

    Row(

        modifier = Modifier

            .clip(RoundedCornerShape(50.dp))

            .background(Color(0xFFF5E3DB))

            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(

            imageVector = icon,

            contentDescription = null,

            tint = Color(0xFF2E2E2E),

            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(

            text = text,

            fontSize = 18.sp,

            fontFamily = FontFamily.Serif
        )
    }
}