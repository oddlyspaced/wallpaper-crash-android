package com.example.wallpaper

import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var bmp: Bitmap
    private val luminosityMatrix = floatArrayOf(
        .2126f, .0000f, .0000f, .0000f, .0000f,
        .0000f, .7152f, .0000f, .0000f, .0000f,
        .0000f, .0000f, .0722f, .0000f, .0000f,
        .0000f, .0000f, .0000f, 1.000f, .0000f )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bmp = BitmapFactory.decodeResource(resources, R.drawable.wallpaper)
        getHistogram(toGrayscale(bmp))
    }

    private fun toGrayscale(bmp: Bitmap): Bitmap {
        val width = bmp.width
        val height = bmp.height
        val grayscale = Bitmap.createBitmap(width, height, bmp.config)
        val canvas = Canvas(grayscale)
        val colorMatrix = ColorMatrix(luminosityMatrix)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(bmp, Matrix(), paint)
        return grayscale
    }

    private fun getHistogram(grayscale: Bitmap) {
        val width = grayscale.width
        val height = grayscale.height

        val histogram = Array(256) { _ -> 0}

        for (row in 0 until height) {
            for (col in 0 until width) {
                val pixel = grayscale.getPixel(col, row)
                try {
                    val y = Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)
                    histogram[y]++
                }
                catch (e: Exception) {
                    val px = bmp.getPixel(col, row)
                    Log.e("ORIGINAL-", "$col, $row: ${Color.red(px)}, ${Color.green(px)}, ${Color.blue(px)}, ${Color.alpha(px)}")
                    Log.e("ORDINATES", "$col, $row: ${Color.red(pixel)}, ${Color.green(pixel)}, ${Color.blue(pixel)}")
                }
            }
        }
        //return histogram
    }

}