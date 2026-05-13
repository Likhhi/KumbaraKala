package com.example.kumbarakala.firebase

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

object ProductImageCloudinaryManager {

    /*
        CHANGE THESE
     */
    private const val CLOUD_NAME = "di0zlv1vn"

    private const val UPLOAD_PRESET =
        "kumbara_unsigned"

    suspend fun uploadImage(
        context: Context,
        imageUri: Uri
    ): String? {

        return withContext(Dispatchers.IO) {

            try {

                /*
                    CONVERT URI TO FILE
                 */
                val inputStream =
                    context.contentResolver
                        .openInputStream(imageUri)

                val file =
                    File.createTempFile(
                        "upload",
                        ".jpg",
                        context.cacheDir
                    )

                val outputStream =
                    FileOutputStream(file)

                inputStream?.copyTo(outputStream)

                outputStream.close()

                inputStream?.close()

                /*
                    REQUEST BODY
                 */
                val requestBody =
                    MultipartBody.Builder()
                        .setType(
                            MultipartBody.FORM
                        )

                        .addFormDataPart(
                            "file",
                            file.name,

                            file.asRequestBody(
                                "image/*"
                                    .toMediaTypeOrNull()
                            )
                        )

                        .addFormDataPart(
                            "upload_preset",
                            UPLOAD_PRESET
                        )

                        .build()

                /*
                    REQUEST
                 */
                val request =
                    Request.Builder()

                        .url(
                            "https://api.cloudinary.com/v1_1/$CLOUD_NAME/image/upload"
                        )

                        .post(requestBody)

                        .build()

                /*
                    EXECUTE
                 */
                val response =
                    OkHttpClient()
                        .newCall(request)
                        .execute()

                val responseData =
                    response.body?.string()

                /*
                    GET IMAGE URL
                 */
                if (response.isSuccessful &&
                    responseData != null
                ) {

                    val json =
                        JSONObject(responseData)

                    json.getString(
                        "secure_url"
                    )

                } else {

                    null
                }

            } catch (e: Exception) {

                e.printStackTrace()

                null
            }
        }
    }
}