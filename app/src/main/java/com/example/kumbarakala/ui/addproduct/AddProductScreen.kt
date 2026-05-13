package com.example.kumbarakala.ui.product

import android.net.Uri
import android.widget.Toast
import com.example.kumbarakala.firebase.ProductImageCloudinaryManager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalOffer

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import coil.compose.AsyncImage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import java.util.UUID

/*
    PRODUCT MODEL
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

    val createdAt: Long =
        System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavController
) {

    val context = LocalContext.current

    /*
        FIREBASE
     */

    val firestore =
        FirebaseFirestore.getInstance()

    val currentUser =
        FirebaseAuth.getInstance().currentUser

    /*
        FORM STATES
     */

    var productName by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    /*
        BENEFITS
     */


    val availableBenefits = listOf(
        "pH Balance",
        "Natural Clay",
        "Porous Cooling",
        "Hand-Thrown",
        "Eco-Friendly",
        "100% Organic"
    )
    val categoryBenefitSuggestions = mapOf(

        "Water Pots" to listOf(
            "pH Balance",
            "Porous Cooling"
        ),

        "Kitchenware" to listOf(
            "Natural Clay",
            "Eco-Friendly"
        ),

        "Clay Lamps" to listOf(
            "Hand-Thrown"
        ),

        "Decor" to listOf(
            "Eco-Friendly"
        ),

        "Pottery" to listOf(
            "Natural Clay"
        )
    )
    val selectedBenefits = remember {
        mutableStateListOf<String>()
    }

    /*
        IMAGE
     */

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    /*
        LOADING
     */

    var isLoading by remember {
        mutableStateOf(false)
    }

    /*
        VALIDATION
     */

    var nameError by remember {
        mutableStateOf(false)
    }

    var descriptionError by remember {
        mutableStateOf(false)
    }

    var priceError by remember {
        mutableStateOf(false)
    }

    var imageError by remember {
        mutableStateOf(false)
    }

    /*
        IMAGE PICKER
     */

    val imagePicker =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {

                selectedImageUri = uri

                imageError = false
            }
        }

    Scaffold(

        /*
            TOP BAR
         */

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Add Product",

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription = null,

                            tint = Color(0xFF4E342E)
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF8F4))
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp)
        ) {

            /*
                PRODUCT IMAGE
             */

            Text(
                text = "Product Image *",

                fontSize = 22.sp,

                fontWeight = FontWeight.SemiBold,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color(0xfffffbf7))
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        BorderStroke(
                            1.dp,

                            if (imageError)
                                Color.Red
                            else
                                Color(0xFFE2C7B8)
                        ),

                        RoundedCornerShape(20.dp)
                    )
                    .clickable {

                        imagePicker.launch("image/*")
                    },

                contentAlignment = Alignment.Center
            ) {

                /*
                    IMAGE PREVIEW
                 */

                if (selectedImageUri != null) {

                    AsyncImage(
                        model = selectedImageUri,

                        contentDescription = null,

                        modifier =
                            Modifier.fillMaxSize(),

                        contentScale =
                            ContentScale.Crop
                    )

                } else {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(
                                    Color(0xFFF5E8E0)
                                ),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.CameraAlt,

                                contentDescription = null,

                                tint =
                                    Color(0xFF6D4C41),

                                modifier =
                                    Modifier.size(34.dp)
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.height(18.dp)
                        )

                        Text(
                            text =
                                "Upload Product Image",

                            fontSize = 20.sp,

                            fontWeight =
                                FontWeight.Medium,

                            color =
                                Color(0xFF4E342E)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "Tap to upload from gallery",

                            color = Color.Gray
                        )
                    }
                }
            }

            /*
                IMAGE ERROR
             */

            if (imageError) {

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text =
                        "Product image is required",

                    color = Color.Red,

                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = "Every handcrafted piece carries a cultural story and tradition.",

                fontSize = 14.sp,

                color = Color(0xFF8D6E63),

                lineHeight = 22.sp
            )

            /*
                PRODUCT NAME
             */

            Text(
                text = "Product Name *",

                fontSize = 22.sp,

                fontWeight = FontWeight.SemiBold,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = productName,

                onValueChange = {

                    productName = it

                    nameError = false
                },

                placeholder = {
                    Text(
                        "e.g., Terracotta Water Pot"
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                isError = nameError,

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.LocalOffer,

                        contentDescription = null
                    )
                },

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            /*
                DESCRIPTION
             */

            Text(
                text = "Description *",

                fontSize = 22.sp,

                fontWeight = FontWeight.SemiBold,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,

                onValueChange = {

                    description = it

                    descriptionError = false
                },

                placeholder = {

                    Text(
                        "Share the craftsmanship, materials, tradition and story behind your creation..."
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),

                isError = descriptionError,

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Description,

                        contentDescription = null
                    )
                },

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            /*
     BENEFITS
  */

            Text(
                text = "Benefits / Features",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select the traditional and wellness qualities your craft offers",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(14.dp))



            /*
                BENEFIT CHIPS
             */

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                availableBenefits.forEach { benefit ->

                    val isSelected =
                        selectedBenefits.contains(benefit)

                    Card(
                        modifier = Modifier.clickable {

                            if (isSelected) {
                                selectedBenefits.remove(benefit)
                            } else {
                                selectedBenefits.add(benefit)
                            }
                        },

                        shape = RoundedCornerShape(50.dp),

                        colors = CardDefaults.cardColors(

                            containerColor =

                                if (isSelected)
                                    Color(0xFF9C6B5A)
                                else
                                    Color(0xFFF5E8E0)
                        ),

                        border = BorderStroke(
                            1.dp,

                            if (isSelected)
                                Color(0xFF9C6B5A)
                            else
                                Color(0xFFD7CCC8)
                        )
                    ) {

                        Text(
                            text = benefit,

                            modifier = Modifier.padding(
                                horizontal = 18.dp,
                                vertical = 12.dp
                            ),

                            color =

                                if (isSelected)
                                    Color.White
                                else
                                    Color(0xFF4E342E),

                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /*
                PRICE + CATEGORY
             */

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                /*
                    PRICE
                 */

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Price (₹) *",

                        fontSize = 20.sp,

                        fontWeight =
                            FontWeight.SemiBold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    OutlinedTextField(
                        value = price,

                        onValueChange = {

                            if (
                                it.all { char ->
                                    char.isDigit()
                                }
                            ) {

                                price = it
                            }

                            priceError = false
                        },

                        placeholder = {
                            Text("850")
                        },

                        isError = priceError,

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default
                                        .CurrencyRupee,

                                contentDescription =
                                    null
                            )
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                /*
                    CATEGORY
                 */

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Category",

                        fontSize = 20.sp,

                        fontWeight =
                            FontWeight.SemiBold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    val categoryOptions = listOf(
                        "Pottery",
                        "Water Pots",
                        "Kitchenware",
                        "Decor",
                        "Clay Lamps"
                    )

                    var categoryExpanded by remember {
                        mutableStateOf(false)
                    }

                    Box {

                        OutlinedButton(
                            onClick = {
                                categoryExpanded = true
                            },

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(16.dp),

                            border = BorderStroke(
                                1.dp,
                                Color(0xFFD7CCC8)
                            )
                        ) {

                            Text(
                                text =
                                    if (category.isBlank())
                                        "Select Category"
                                    else
                                        category,

                                color = Color(0xFF4E342E)
                            )
                        }

                        DropdownMenu(
                            expanded = categoryExpanded,

                            onDismissRequest = {
                                categoryExpanded = false
                            }
                        ) {

                            categoryOptions.forEach { option ->

                                DropdownMenuItem(

                                    text = {
                                        Text(option)
                                    },

                                    onClick = {

                                        category = option

                                        selectedBenefits.clear()

                                        categoryBenefitSuggestions[option]
                                            ?.let { suggestedBenefits ->

                                                selectedBenefits.addAll(
                                                    suggestedBenefits
                                                )
                                            }

                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            /*
                TIP CARD
             */

            Card(
                colors = CardDefaults.cardColors(
                    containerColor =
                        Color(0xFFFFF8E8)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Lightbulb,

                        contentDescription = null,

                        tint = Color(0xFFE6A700)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text =
                            "Stories, materials and wellness benefits help customers appreciate the heritage behind your craft.",
                        color = Color(0xFF4E342E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            /*
                SAVE BUTTON
             */

            Button(

                onClick = {

                    /*
                        VALIDATION
                     */

                    nameError =
                        productName
                            .trim()
                            .isBlank()

                    descriptionError = description
                        .trim()
                        .isBlank()

                    priceError =
                        price
                            .trim()
                            .isBlank() ||

                                price.toIntOrNull() == null ||

                                price.toInt() <= 0

                    imageError =
                        selectedImageUri == null

                    if (
                        nameError ||
                        descriptionError ||
                        priceError ||
                        imageError
                    ) {

                        Toast.makeText(
                            context,
                            "Please fill valid product details",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    /*
                        USER CHECK
                     */

                    if (currentUser == null) {

                        Toast.makeText(
                            context,
                            "User not authenticated",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    /*
                        LOADING
                     */

                    isLoading = true

                    selectedImageUri?.let { uri ->

                        CoroutineScope(
                            Dispatchers.Main
                        ).launch {

                            val imageUrl =

                                ProductImageCloudinaryManager.uploadImage(
                                    context = context,
                                    imageUri = uri
                                )

                            if (imageUrl != null) {

                                val productId =
                                    UUID.randomUUID().toString()

                                val product = Product(

                                    productId = productId,

                                    artisanId =
                                        currentUser.uid,

                                    name =
                                        productName.trim(),

                                    description =
                                        description.trim(),

                                    benefits = selectedBenefits,

                                    price =
                                        price.trim(),

                                    category =
                                        category.trim(),

                                    imageUrl = imageUrl,

                                    createdAt =
                                        System.currentTimeMillis()
                                )

                                firestore
                                    .collection("products")
                                    .document(productId)
                                    .set(product)

                                    .addOnSuccessListener {

                                        isLoading = false

                                        Toast.makeText(
                                            context,
                                            "Product Added Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        navController.popBackStack()
                                    }

                                    .addOnFailureListener {

                                        isLoading = false

                                        Toast.makeText(
                                            context,
                                            "Firestore Upload Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            } else {

                                isLoading = false

                                Toast.makeText(
                                    context,
                                    "Cloudinary Upload Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },

                enabled = !isLoading,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFF6D3D2A)
                )
            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        color = Color.White,

                        modifier =
                            Modifier.size(24.dp),

                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = "Publish Craft Story",

                        fontSize = 20.sp,

                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}