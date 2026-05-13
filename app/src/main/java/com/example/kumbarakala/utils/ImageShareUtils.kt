package com.example.kumbarakala.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object ImageShareUtils {

    fun shareBitmap(
        context: Context,
        bitmap: Bitmap
    ) {

        try {

            val file = File(
                context.cacheDir,
                "story_card.png"
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

            val intent = Intent().apply {

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
                    intent,
                    "Export Story Card"
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}