// PART 1

package com.example.kumbarakala.ui.storycard

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.kumbarakala.model.StoryCard
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PreviewStoryCardScreen(
    navController: NavController,
    productId: String
) {

    val context = LocalContext.current

    val firestore = FirebaseFirestore.getInstance()

    var storyCard by remember {
        mutableStateOf<StoryCard?>(null)
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var captureView: View? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(Unit) {

        firestore
            .collection("story_cards")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->

                storyCard =
                    document.toObject(StoryCard::class.java)

                isLoading = false
            }

            .addOnFailureListener {

                isLoading = false
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

        val card = storyCard

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F1EA))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
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
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription = null,

                        tint = Color(0xFF3A2318)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {

                    Text(
                        text = "Preview Story Card",

                        fontSize = 28.sp,

                        fontWeight = FontWeight.ExtraBold,

                        color = Color(0xFF3A2318)
                    )

                    Text(
                        text = "Luxury artisan storytelling",

                        color = Color.Gray,

                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AndroidView(

                modifier = Modifier.fillMaxWidth(),

                factory = { context ->

                    ComposeView(context).apply {

                        setContent {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),

                                shape = RoundedCornerShape(28.dp),

                                elevation = CardDefaults.cardElevation(10.dp)
                            ) {

                                Column {

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(340.dp)
                                    ) {

                                        Image(
                                            painter =
                                                rememberAsyncImagePainter(
                                                    model = ImageRequest.Builder(context)
                                                        .data(card?.productImage)
                                                        .allowHardware(false)
                                                        .build()
                                                ),

                                            contentDescription = null,

                                            modifier = Modifier.fillMaxSize(),

                                            contentScale = ContentScale.Crop
                                        )

                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        colors = listOf(
                                                            Color.Transparent,
                                                            Color.Black.copy(alpha = 0.82f)
                                                        )
                                                    )
                                                )
                                        )

                                        Column(
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(18.dp),

                                            horizontalAlignment =
                                                Alignment.End
                                        ) {

                                            Text(
                                                text = "Kumbara\nKala",

                                                color = Color.White,

                                                fontSize = 24.sp,

                                                fontWeight = FontWeight.Bold
                                            )

                                            Spacer(
                                                modifier = Modifier.height(4.dp)
                                            )

                                            Text(
                                                text = "MODERN ARTISAN",

                                                color = Color(0xFFE8C9AE),

                                                fontSize = 12.sp,

                                                letterSpacing = 2.sp
                                            )
                                        }

                                        Column(
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .padding(24.dp)
                                        ) {

                                            Text(
                                                text =
                                                    "ANCIENT TRADITION SERIES",

                                                color = Color(0xFFD7B08A),

                                                fontSize = 12.sp,

                                                letterSpacing = 2.sp
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Text(
                                                text =
                                                    card?.productName ?: "",

                                                color = Color.White,

                                                fontSize = 34.sp,

                                                lineHeight = 42.sp,

                                                fontWeight =
                                                    FontWeight.ExtraBold
                                            )
                                        }
                                    }
                                    // PART 2

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFFF8F2EB))
                                            .padding(20.dp)
                                    ) {

                                        Row {

                                            if (!card?.artisanImageUrl.isNullOrEmpty()) {

                                                Image(
                                                    painter =
                                                        rememberAsyncImagePainter(
                                                            model = ImageRequest.Builder(context)
                                                                .data(card?.artisanImageUrl)
                                                                .allowHardware(false)
                                                                .build()
                                                        ),

                                                    contentDescription = null,

                                                    modifier = Modifier
                                                        .size(90.dp)
                                                        .clip(CircleShape),

                                                    contentScale =
                                                        ContentScale.Crop
                                                )

                                            } else {

                                                Box(
                                                    modifier = Modifier
                                                        .size(90.dp)
                                                        .clip(CircleShape)
                                                        .background(
                                                            Color(0xFFD8B7A0)
                                                        ),

                                                    contentAlignment =
                                                        Alignment.Center
                                                ) {

                                                    Text(
                                                        text =
                                                            card?.artisanName
                                                                ?.firstOrNull()
                                                                ?.uppercase()
                                                                ?: "A",

                                                        color = Color.White,

                                                        fontSize = 34.sp,

                                                        fontWeight =
                                                            FontWeight.Bold
                                                    )
                                                }
                                            }

                                            Spacer(
                                                modifier = Modifier.width(18.dp)
                                            )

                                            Column(
                                                modifier = Modifier.weight(1f)
                                            ) {

                                                Text(
                                                    text =
                                                        card?.artisanName ?: "",

                                                    fontSize = 28.sp,

                                                    fontWeight =
                                                        FontWeight.ExtraBold,

                                                    color = Color(0xFF3A2318)
                                                )

                                                Spacer(
                                                    modifier = Modifier.height(4.dp)
                                                )

                                                Text(
                                                    text = "Master Craftsman",

                                                    color = Color(0xFF7B5A4D),

                                                    fontSize = 16.sp
                                                )

                                                Spacer(
                                                    modifier = Modifier.height(8.dp)
                                                )

                                                Column {

                                                    Row(
                                                        verticalAlignment =
                                                            Alignment.CenterVertically
                                                    ) {

                                                        Icon(
                                                            imageVector =
                                                                Icons.Default.LocationOn,

                                                            contentDescription = null,

                                                            tint =
                                                                Color(0xFF9A5C44),

                                                            modifier =
                                                                Modifier.size(16.dp)
                                                        )

                                                        Spacer(
                                                            modifier = Modifier.width(4.dp)
                                                        )

                                                        Text(
                                                            text =
                                                                card?.artisanLocation
                                                                    ?: "",

                                                            color =
                                                                Color(0xFF6A4E42)
                                                        )

                                                    }

                                                    Spacer(
                                                        modifier = Modifier.height(6.dp)
                                                    )
                                                    Text(
                                                        text =
                                                            card?.artisanPhone
                                                                ?: "",

                                                        fontSize = 18.sp,

                                                        fontWeight = FontWeight.SemiBold,

                                                        color = Color(0xFF3A2318)
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(
                                            modifier = Modifier.height(20.dp)
                                        )

                                        Text(
                                            text =
                                                card?.summarizedQuote
                                                    ?: "",

                                            fontSize = 18.sp,

                                            lineHeight = 30.sp,

                                            fontStyle = FontStyle.Italic,

                                            color = Color(0xFF5B392A)
                                        )

                                        Spacer(
                                            modifier = Modifier.height(24.dp)
                                        )

                                        Text(
                                            text = "The Story",

                                            fontSize = 24.sp,

                                            fontWeight = FontWeight.Bold,

                                            color = Color(0xFF3A2318)
                                        )

                                        Spacer(
                                            modifier = Modifier.height(10.dp)
                                        )

                                        Text(
                                            text =
                                                card?.generatedStory ?: "",

                                            fontSize = 16.sp,

                                            lineHeight = 28.sp,

                                            color = Color(0xFF4E3A31)
                                        )

                                        Spacer(
                                            modifier = Modifier.height(24.dp)
                                        )

                                        FlowRow(
                                            horizontalArrangement =
                                                Arrangement.spacedBy(10.dp),

                                            verticalArrangement =
                                                Arrangement.spacedBy(10.dp)
                                        ) {

                                            card?.benefits?.forEach { benefit ->

                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(50.dp))
                                                        .background(Color(0xFF4E2C1E))
                                                        .padding(
                                                            horizontal = 18.dp,
                                                            vertical = 10.dp
                                                        )
                                                ) {

                                                    Text(
                                                        text = benefit,

                                                        color = Color.White,

                                                        fontSize = 14.sp,

                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        captureView = this
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    try {

                        captureView?.let { exportView ->

                            val bitmap =
                                Bitmap.createBitmap(
                                    exportView.width,
                                    exportView.height,
                                    Bitmap.Config.ARGB_8888
                                )

                            val canvas = Canvas(bitmap)

                            exportView.draw(canvas)

                            val file = File(
                                context.cacheDir,
                                "story_card_export.png"
                            )

                            val outputStream =
                                FileOutputStream(file)

                            bitmap.compress(
                                Bitmap.CompressFormat.PNG,
                                100,
                                outputStream
                            )

                            outputStream.flush()

                            outputStream.close()

                            val uri =
                                FileProvider.getUriForFile(
                                    context,
                                    context.packageName + ".provider",
                                    file
                                )

                            val shareIntent = Intent().apply {

                                action = Intent.ACTION_SEND

                                type = "image/png"

                                putExtra(
                                    Intent.EXTRA_STREAM,
                                    uri
                                )

                                addFlags(
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                            }

                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Export Story Card"
                                )
                            )
                        }

                    } catch (e: Exception) {

                        Toast.makeText(
                            context,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6D3D2A)
                )
            ) {

                Text(
                    text = "Export as Image",

                    color = Color.White,

                    fontSize = 20.sp,

                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}