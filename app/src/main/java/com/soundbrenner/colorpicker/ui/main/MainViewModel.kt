package com.soundbrenner.colorpicker.ui.main

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    fun getBitmapFromView(view: View): Bitmap {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun getSelectedColor(bitmap: Bitmap, x: Int, y: Int): Int {
        val pixel = bitmap.getPixel(x, y)

        val r = pixel.let { Color.red(it) }
        val g = pixel.let { Color.green(it) }
        val b = pixel.let { Color.blue(it) }

        return Color.rgb(r, g, b)

    }


}