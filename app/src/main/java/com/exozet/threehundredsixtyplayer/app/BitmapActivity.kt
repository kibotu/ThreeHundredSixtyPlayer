package com.exozet.threehundredsixtyplayer.app

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixtyplayer.app.databinding.SampleViewBinding
import com.exozet.threehundredsixtyplayer.loadImage
import com.exozet.threehundredsixtyplayer.parseAssetFile

class BitmapActivity : AppCompatActivity() {

    private lateinit var binding: SampleViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SampleViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sample1 = "interior_example.jpg"
        val sample2 = "equirectangular.jpg"
        var current = sample2

        var image1: Bitmap? = null
        var image2: Bitmap? = null
        var current2: Bitmap?

        sample1.parseAssetFile().loadImage(this) {
            image1 = it
            binding.threeHundredSixtyView.bitmap = it
        }

        sample2.parseAssetFile().loadImage(this) {
            image2 = it
        }

        binding.next.setOnClickListener {

            if (current == sample1) {
                current = sample2
                current2 = image2
            } else {
                current = sample1
                current2 = image1
            }

            Log.v("ThreeHundredSixty", "current=$current")

            binding.threeHundredSixtyView.bitmap = current2
        }
    }
}