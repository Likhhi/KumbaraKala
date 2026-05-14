package com.example.kumbarakala.ui.storycard
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.kumbarakala.model.Product
import com.example.kumbarakala.model.StoryCard
import com.example.kumbarakala.utils.GeminiManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@Composable
fun GenerateStoryCardScreen(
    navController: NavController,
    productId: String
) {

    val context = LocalContext.current


    val firestore = FirebaseFirestore.getInstance()

    val auth = FirebaseAuth.getInstance()

    var product by remember {
        mutableStateOf<Product?>(null)
    }

    var artisanName by remember {
        mutableStateOf("")
    }

    var artisanLocation by remember {
        mutableStateOf("")
    }
    var artisanPhone by remember {
        mutableStateOf("")
    }

    var artisanQuote by remember {
        mutableStateOf("")
    }

    var generatedStory by remember {
        mutableStateOf(
            "Luxury artisan storytelling will appear here."
        )
    }

    var summarizedQuote by remember {
        mutableStateOf(
            "Premium artisan summary will appear here."
        )
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {

        firestore
            .collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->

                product =
                    document.toObject(Product::class.java)

                val currentUser =
                    auth.currentUser

                product?.artisanId?.let { artisanId ->

                    firestore
                        .collection("users")
                        .document(artisanId)
                        .get()
                        .addOnSuccessListener { userDoc ->
                            artisanName =
                                userDoc.getString("name")
                                    ?: ""

                            artisanLocation =
                                userDoc.getString("location")
                                    ?: ""


                            artisanPhone =
                                userDoc.getString("phone")
                                    ?: ""

                            isLoading = false
                        }
                }
            }
    }

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
                .padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Generate Story Card",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4E2D1F)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = rememberAsyncImagePainter(
                    product?.imageUrl
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = product?.name ?: "",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF2E1A12)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "₹${product?.price}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A3E1D)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(22.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.Description,
                            contentDescription = null,
                            tint = Color(0xFF6D3D2A)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Luxury Preview",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = summarizedQuote,
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        color = Color(0xFF5B392A),

                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = generatedStory,
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        color = Color(0xFF4E3A31),

                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {

                    isLoading = true

                    CoroutineScope(
                        Dispatchers.IO
                    ).launch {

                        try {

                            val aiStory =
                                GeminiManager.generateStory(
                                    productName = product?.name ?: "",
                                    description = product?.description ?: "",
                                    artisanName = artisanName,
                                    location = artisanLocation
                                )

                            val aiSummary =
                                GeminiManager.summarizeArtisanStory(
                                    artisanQuote
                                )

                            generatedStory = aiStory

                            summarizedQuote = aiSummary

                            val storyCard = StoryCard(

                                productId = productId,

                                artisanId =
                                    auth.currentUser?.uid ?: "",

                                productName =
                                    product?.name ?: "",

                                productImage =
                                    product?.imageUrl ?: "",

                                productPrice =
                                    product?.price ?: "",

                                productDescription =
                                    product?.description ?: "",

                                artisanName = artisanName,

                                artisanLocation = artisanLocation,
                                artisanPhone = artisanPhone,



                                artisanQuote = artisanQuote,

                                summarizedQuote = aiSummary,

                                generatedStory = aiStory,

                                benefits =
                                    product?.benefits
                                        ?: emptyList()
                            )

                            firestore
                                .collection("story_cards")
                                .document(productId)
                                .set(storyCard)
                                .addOnSuccessListener {

                                    isLoading = false

                                    Toast.makeText(
                                        context,
                                        "Heritage Story Generated",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate(
                                        "preview_story/$productId"
                                    )
                                }

                        } catch (e: Exception) {

                            isLoading = false

                            Toast.makeText(
                                context,
                                e.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6D3D2A)
                )
            ) {

                Icon(
                    Icons.Default.AutoAwesome,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Generate Heritage Story",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}