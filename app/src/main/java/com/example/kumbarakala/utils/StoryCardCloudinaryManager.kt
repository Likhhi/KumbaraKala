package com.example.kumbarakala.utils

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager

object StoryCardCloudinaryManager {

    fun init(context: Context) {

        val config = mapOf(

            "cloud_name" to "YOUR_CLOUD_NAME",

            "api_key" to "YOUR_API_KEY",

            "api_secret" to "YOUR_API_SECRET"
        )

        MediaManager.init(
            context,
            config
        )
    }

    fun uploadImage(
        imageUri: Uri,
        onSuccess: (String) -> Unit
    ) {

        MediaManager.get()
            .upload(imageUri)
            .callback(object :
                com.cloudinary.android.callback.UploadCallback {

                override fun onStart(requestId: String?) {}

                override fun onProgress(
                    requestId: String?,
                    bytes: Long,
                    totalBytes: Long
                ) {}

                override fun onSuccess(
                    requestId: String?,
                    resultData: MutableMap<Any?, Any?>?
                ) {

                    val url =
                        resultData?.get("secure_url")
                            .toString()

                    onSuccess(url)
                }

                override fun onError(
                    requestId: String?,
                    error: com.cloudinary.android.callback.ErrorInfo?
                ) {}

                override fun onReschedule(
                    requestId: String?,
                    error: com.cloudinary.android.callback.ErrorInfo?
                ) {}
            })
            .dispatch()
    }
}