package com.example.kumbarakala.ui.catalog

import android.content.Context

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import coil.compose.AsyncImage

import com.example.kumbarakala.R
import com.example.kumbarakala.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.clickable
@Composable
fun CatalogScreen(
    navController: NavController,
    onProductClick: (Product) -> Unit
) {

    /*
        CONTEXT
     */

    val context = LocalContext.current

    /*
        SHARED PREFERENCES
     */

    val sharedPreferences =
        context.getSharedPreferences(
            "KumbaraKalaPrefs",
            Context.MODE_PRIVATE
        )

    /*
        ROLE
     */

    val role =
        sharedPreferences.getString(
            "USER_ROLE",
            "customer"
        ) ?: "customer"

    /*
        FIREBASE
     */

    val firestore =
        FirebaseFirestore.getInstance()

    val currentUser =
        FirebaseAuth.getInstance().currentUser

    /*
        SEARCH
     */

    var searchText by remember {
        mutableStateOf("")
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    /*
        PRODUCTS
     */

    val products = remember {
        mutableStateListOf<Product>()
    }

    /*
        LOADING
     */

    var isLoading by remember {
        mutableStateOf(true)
    }

    /*
        FETCH PRODUCTS
     */

    LaunchedEffect(Unit) {

        val query =

            if (role == "artisan") {

                firestore
                    .collection("products")
                    .whereEqualTo(
                        "artisanId",
                        currentUser?.uid
                    )

            } else {

                firestore
                    .collection("products")
            }

        query
            .get()

            .addOnSuccessListener { documents ->

                products.clear()

                for (document in documents) {

                    val product =
                        document.toObject(
                            Product::class.java
                        )

                    products.add(product)
                }

                isLoading = false
            }

            .addOnFailureListener {

                isLoading = false
            }
    }
    val categories = listOf("All") + products
        .map { it.category }
        .distinct()
        .filter { it.isNotBlank() }
    /*
        FILTER PRODUCTS
     */

    val filteredProducts = products.filter { product ->

        val matchesSearch =

            product.name.contains(
                searchText,
                ignoreCase = true
            ) ||

                    product.category.contains(
                        searchText,
                        ignoreCase = true
                    )

        val matchesCategory =

            selectedCategory == "All" ||

                    product.category.equals(
                        selectedCategory,
                        ignoreCase = true
                    )

        matchesSearch && matchesCategory
    }

    /*
        SCREEN
     */

    Scaffold(

        /*
            BOTTOM NAVIGATION
         */

        bottomBar = {

            if (role == "artisan") {

                NavigationBar {

                    /*
                        CATALOG
                     */

                    NavigationBarItem(
                        selected = true,

                        onClick = { },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Home,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Catalog")
                        }
                    )

                    /*
                        ADD PRODUCT
                     */

                    NavigationBarItem(
                        selected = false,

                        onClick = {

                            navController.navigate(
                                "add_product"
                            )
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Add,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Add")
                        }
                    )

                    /*
                        PROFILE
                     */

                    NavigationBarItem(
                        selected = false,

                        onClick = {
                            navController.navigate("profile")
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Person,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Profile")
                        }
                    )
                }
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF8F4))
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            /*
                TITLE
             */

            Text(
                text = "Kumbara Kala",

                fontSize = 34.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(14.dp))

            /*
                SEARCH BAR
             */

            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = searchText,

                    onValueChange = {
                        searchText = it
                    },

                    placeholder = {

                        Text(
                            "Search pottery, bowls, vases..."
                        )
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.Search,

                            contentDescription = null
                        )
                    },

                    modifier = Modifier.weight(1f),

                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box {

                    Button(
                        onClick = {
                            expanded = true
                        },

                        shape = RoundedCornerShape(16.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF8D6E63)
                        )
                    ) {

                        Text(
                            text = selectedCategory,
                            color = Color.White
                        )                    }

                    DropdownMenu(
                        expanded = expanded,

                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        categories.forEach { category ->

                            DropdownMenuItem(

                                text = {
                                    Text(category)
                                },

                                onClick = {

                                    selectedCategory = category

                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            /*
                CUSTOMER BANNER
             */

            if (role == "customer") {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),

                    shape = RoundedCornerShape(20.dp)
                ) {

                    Box {

                        Image(
                            painter =
                                painterResource(
                                    id = R.drawable.banner
                                ),

                            contentDescription = null,

                            modifier =
                                Modifier.fillMaxSize(),

                            contentScale =
                                ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .align(
                                    Alignment.BottomStart
                                )
                                .padding(16.dp)
                        ) {

                            Text(
                                text = "NEW ARRIVAL",

                                color = Color.White,

                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(
                                text =
                                    "Earthy Essentials",

                                color = Color.White,

                                fontSize = 24.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            /*
                HEADING
             */

            Text(
                text =
                    if (role == "artisan")
                        "My Products"
                    else
                        "Handcrafted Catalog",

                fontSize = 30.sp,

                fontWeight = FontWeight.SemiBold,

                color = Color(0xFF3E2723)
            )

            Spacer(modifier = Modifier.height(20.dp))

            /*
                LOADING STATE
             */

            if (isLoading) {

                Box(
                    modifier = Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = Color(0xFF6D3D2A)
                    )
                }

            } else {

                /*
                    EMPTY STATE
                 */

                if (filteredProducts.isEmpty()) {

                    Box(
                        modifier = Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                "No Handcrafted stories yet",

                            fontSize = 22.sp,

                            color = Color.Gray
                        )
                    }

                } else {

                    /*
                        PRODUCT GRID
                     */

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),

                        verticalArrangement =
                            Arrangement.spacedBy(14.dp),

                        horizontalArrangement =
                            Arrangement.spacedBy(14.dp),

                        modifier = Modifier.fillMaxSize()
                    ) {

                        items(filteredProducts) { product ->

                            Card(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onProductClick(product)
                                    },

                                shape =
                                    RoundedCornerShape(26.dp),

                                colors =
                                    CardDefaults.cardColors(
                                        containerColor =
                                            Color(0xFFF5EBDD)
                                    )
                            ) {

                                Column {

                                    /*
                                        PRODUCT IMAGE
                                     */

                                    AsyncImage(
                                        model =
                                            product.imageUrl,

                                        contentDescription =
                                            null,

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(220.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 18.dp,
                                                    topEnd = 18.dp
                                                )
                                            ),

                                        contentScale =
                                            ContentScale.Crop
                                    )

                                    /*
                                        PRODUCT DETAILS
                                     */

                                    Column(
                                        modifier =
                                            Modifier.padding(
                                                12.dp
                                            )
                                    ) {

                                        Text(
                                            text =
                                                product.name,

                                            fontSize = 17.sp,

                                            fontWeight =
                                                FontWeight.SemiBold,

                                            color =
                                                Color(0xFF2D1810)
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.height(
                                                    6.dp
                                                )
                                        )

                                        Text(
                                            text =
                                                "₹${product.price}",

                                            fontSize = 16.sp,

                                            fontWeight =
                                                FontWeight.Bold,

                                            color =
                                                Color(
                                                    0xFF6D3D2A
                                                )
                                        )
                                        Text(
                                            text = product.benefits.joinToString(", "),
                                            fontSize = 13.sp,
                                            color = Color(0xFF5D4037),
                                            maxLines = 1
                                        )

                                        /*
                                            CATEGORY
                                         */

                                        if (
                                            product.category
                                                .isNotBlank()
                                        ) {

                                            Spacer(
                                                modifier =
                                                    Modifier.height(
                                                        4.dp
                                                    )
                                            )

                                            Text(
                                                text =
                                                    product.category,

                                                fontSize =
                                                    13.sp,

                                                color =
                                                    Color(0xFF8D6E63)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
