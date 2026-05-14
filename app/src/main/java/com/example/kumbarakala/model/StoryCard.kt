package com.example.kumbarakala.model

data class StoryCard(

    val productId: String = "",

    val artisanId: String = "",

    val productName: String = "",

    val productImage: String = "",

    val productPrice: String = "",

    val productDescription: String = "",

    val artisanName: String = "",

    val artisanLocation: String = "",
    val artisanPhone: String = "",

    val artisanImageUrl: String = "",

    val title: String = "",

    val subtitle: String = "",

    val artisanQuote: String = "",

    val summarizedQuote: String = "",

    val generatedStory: String = "",

    val benefits: List<String> = emptyList(),

    val material: String = "",

    val tradition: String = "",

    val sustainability: String = "",

    val createdAt: Long =
        System.currentTimeMillis()
)