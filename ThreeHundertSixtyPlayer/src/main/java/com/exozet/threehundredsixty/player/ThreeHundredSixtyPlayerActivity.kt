package com.exozet.threehundredsixty.player

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils.isEmpty
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

        glView.setmRotateInertia(RotateInertia.INERTIA_50)

        // we have no data
        if (isEmpty(intent?.extras?.getString(ASSET_FILE_PATH))
                && isEmpty(intent?.extras?.getString(FILE_PATH))) {
            finish()
        }

        // load image by assets
        intent?.extras?.getString(ThreeHundredSixtyPlayerActivity.ASSET_FILE_PATH)?.let {
            glView.setTexture(Photo(getBitmapFromAsset(it)))
        }

        // load image by file
        intent?.extras?.getString(ThreeHundredSixtyPlayerActivity.FILE_PATH)?.let {
            glView.setTexture(Photo(getBitmapFromFile(it)))
        }
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

    private fun getBitmapFromFile(filePath: String): Bitmap? {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(filePath, options)
    }

    companion object {
        const val ASSET_FILE_PATH: String = "ASSET_FILE_PATH"
        const val FILE_PATH: String = "FILE_PATH"
    }
}