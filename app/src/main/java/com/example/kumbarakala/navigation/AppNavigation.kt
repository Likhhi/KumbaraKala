package com.example.kumbarakala.navigation

import androidx.compose.runtime.*

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kumbarakala.ui.storycard.GenerateStoryCardScreen
import com.example.kumbarakala.ui.storycard.PreviewStoryCardScreen
import com.example.kumbarakala.model.Product
import com.example.kumbarakala.ui.profile.ProfileScreen
import com.example.kumbarakala.ui.auth.LoginScreen
import com.example.kumbarakala.ui.auth.RegisterScreen
import com.example.kumbarakala.ui.catalog.CatalogScreen
import com.example.kumbarakala.ui.product.AddProductScreen
import com.example.kumbarakala.ui.productdetails.ProductDetailsScreen
import com.example.kumbarakala.ui.role.RoleSelectionScreen
import com.example.kumbarakala.ui.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {

    /*
        ROLE STATE
     */

    var isArtisan by remember {
        mutableStateOf(false)
    }

    /*
        SELECTED PRODUCT
     */

    var selectedProduct by remember {

        mutableStateOf(
            Product()
        )
    }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        /*
            SPLASH
         */

        composable("splash") {

            SplashScreen(navController)
        }

        /*
            ROLE SELECTION
         */

        composable("role_selection") {

            RoleSelectionScreen(

                navController = navController,

                onRoleSelected = { artisanSelected ->

                    isArtisan = artisanSelected
                }
            )
        }

        /*
            LOGIN
         */

        composable("login") {

            LoginScreen(navController)
        }

        /*
            REGISTER
         */

        composable("register") {

            RegisterScreen(navController)
        }

        /*
            CATALOG
         */

        composable("catalog") {

            CatalogScreen(

                navController = navController,

                onProductClick = { product ->

                    selectedProduct = product

                    navController.navigate(
                        "product_details"
                    )
                }
            )
        }

        /*
            ADD PRODUCT
         */

        composable("add_product") {

            AddProductScreen(navController)
        }

        /*
            PRODUCT DETAILS
         */

        composable("product_details") {

            ProductDetailsScreen(

                navController = navController,

                product = selectedProduct,

                isArtisan = isArtisan
            )
        }
        composable("profile") {

            ProfileScreen(navController)
        }
        composable(
            route = "generate_story/{productId}"
        ) { backStackEntry ->

            val productId =
                backStackEntry.arguments
                    ?.getString("productId")
                    ?: ""

            GenerateStoryCardScreen(
                navController = navController,
                productId = productId
            )
        }

        composable(
            route = "preview_story/{productId}"
        ) { backStackEntry ->

            val productId =
                backStackEntry.arguments
                    ?.getString("productId")
                    ?: ""

            PreviewStoryCardScreen(
                navController = navController,
                productId = productId
            )
        }
    }
}