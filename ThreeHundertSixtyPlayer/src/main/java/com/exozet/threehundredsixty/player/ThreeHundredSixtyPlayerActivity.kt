package com.exozet.threehundredsixty.player

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theta360.sample.v2.model.Photo
import com.theta360.sample.v2.model.RotateInertia
import kotlinx.android.synthetic.main.activity_threehundredsixty_player.*
import java.io.IOException
import java.io.InputStream


class ThreeHundredSixtyPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threehundredsixty_player)

        glView.setTexture(Photo(getBitmapFromAsset(intent?.extras?.getString("ASSET_FILE_NAME")
                ?: return)))
        glView.setmRotateInertia(RotateInertia.INERTIA_50)
    }

    private fun getBitmapFromAsset(filePath: String): Bitmap? {
        val assetManager = assets

        val istr: InputStream
        var bitmap: Bitmap? = null
        try {
            istr = assetManager.open(filePath)
            bitmap = BitmapFactory.decodeStream(istr)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bitmap
    }
}