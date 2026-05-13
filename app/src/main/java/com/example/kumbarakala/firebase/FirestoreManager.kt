package com.example.kumbarakala.firebase

import com.example.kumbarakala.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreManager {

    private val firestore =
        FirebaseFirestore.getInstance()

    /*
        SAVE PRODUCT
     */

    fun saveProduct(
        product: Product,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        firestore
            .collection("products")
            .document(product.productId)
            .set(product)

            .addOnSuccessListener {
                onSuccess()
            }

            .addOnFailureListener {
                onFailure(it)
            }
    }

    /*
        FETCH ALL PRODUCTS
     */

    fun fetchAllProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        firestore
            .collection("products")
            .get()

            .addOnSuccessListener { result ->

                val productList =
                    result.documents.mapNotNull {

                        it.toObject(Product::class.java)
                    }

                onSuccess(productList)
            }

            .addOnFailureListener {
                onFailure(it)
            }
    }

    /*
        FETCH ARTISAN PRODUCTS
     */

    fun fetchArtisanProducts(
        artisanId: String,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        firestore
            .collection("products")

            .whereEqualTo(
                "artisanId",
                artisanId
            )

            .get()

            .addOnSuccessListener { result ->

                val productList =
                    result.documents.mapNotNull {

                        it.toObject(Product::class.java)
                    }

                onSuccess(productList)
            }

            .addOnFailureListener {
                onFailure(it)
            }
    }
}