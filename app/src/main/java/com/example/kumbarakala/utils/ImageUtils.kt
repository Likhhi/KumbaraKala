package com.example.kumbarakala.utils


import android.graphics.Bitmap
import android.view.View

object ImageUtils {

    fun captureView(view: View): Bitmap {

        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = android.graphics.Canvas(bitmap)

        view.draw(canvas)

        return bitmap
    }
}