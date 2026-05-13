package com.example.kumbarakala.model

import okhttp3.HttpUrl

data class Product(

    val productId: String = "",
    val artisanName: String = "",
    val artisanId: String = "",
    val artisanImageUrl: String = "",
    val name: String = "",

    val description: String = "",

    val benefits: List<String> = emptyList(),

    val price: String = "",

    val category: String = "",

    val imageUrl: String = "",

    val createdAt: Long = System.currentTimeMillis()
)