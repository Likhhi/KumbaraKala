package com.example.kumbarakala.utils

object GeminiManager {

    /*
        PRODUCT BASED STORY GENERATOR
     */

    suspend fun generateStory(
        productName: String,
        description: String,
        artisanName: String,
        location: String
    ): String {

        return when {

            productName.contains("lamp", true) -> {

                """
$productName is carefully handcrafted by $artisanName from $location, preserving generations of traditional clay artistry.

Its warm earthy texture and timeless handcrafted finish create a calming atmosphere while celebrating authentic rural craftsmanship and sustainable living.

Inspired by ancient artisan traditions, this elegant clay creation brings culture, heritage, and natural beauty into modern homes.
                """.trimIndent()
            }

            productName.contains("pot", true) -> {

                """
Crafted with dedication by $artisanName from $location, this $productName reflects the richness of traditional pottery heritage.

Made using natural clay techniques passed through generations, it supports eco-friendly living while preserving the authentic taste and cooling properties valued in Indian culture.

Its handcrafted elegance symbolizes sustainability, wellness, and timeless artisan craftsmanship.
                """.trimIndent()
            }

            productName.contains("cup", true) -> {

                """
Handmade by skilled artisan $artisanName from $location, this $productName captures the beauty of traditional clay craftsmanship.

Designed with natural materials and authentic hand-finishing techniques, it offers an earthy experience while promoting sustainable and eco-conscious living.

Each handcrafted detail reflects heritage, simplicity, and timeless artistic excellence.
                """.trimIndent()
            }

            productName.contains("bowl", true) -> {

                """
Beautifully handcrafted by $artisanName from $location, this $productName showcases the elegance of traditional clay artistry.

Its earthy texture and handcrafted finish represent generations of artisan heritage while encouraging eco-friendly and healthy living practices.

Blending culture with functionality, this handmade creation reflects timeless craftsmanship and rural artistic excellence.
                """.trimIndent()
            }

            else -> {

                """
$productName is a handcrafted masterpiece created by $artisanName from $location.

Inspired by generations of traditional pottery craftsmanship, this creation reflects cultural heritage, eco-friendly living, and timeless artisan skill.

Carefully shaped using authentic clay techniques, it brings warmth, sustainability, and artistic elegance into modern homes.
                """.trimIndent()
            }
        }
    }

    /*
        ARTISAN STORY SUMMARY
     */

    suspend fun summarizeArtisanStory(
        artisanStory: String
    ): String {

        return """
Preserving generations of traditional craftsmanship, this artisan creation celebrates heritage, sustainability, and the timeless beauty of handmade clay artistry.
        """.trimIndent()
    }
}