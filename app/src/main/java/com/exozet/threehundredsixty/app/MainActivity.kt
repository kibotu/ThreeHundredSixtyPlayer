package com.exozet.threehundredsixty.app

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import com.exozet.parseAssetFile

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // loadFromAssets()
        load()
    }

    private fun load() =
            RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe { granted ->
                if (granted) { // Always true pre-M
                    ThreeHundredSixtyPlayerActivity.Builder
                            .with(this)
                            .uri(parseExternalStorage("/DCIM/large.jpg"))
                            .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE)
                            .interactiveMode(ThreeHundredSixtyPlayer.INTERACTIVE_MODE_TOUCH)
                            .startActivity()
                    finish()
                } else {
                    Snackbar.make(root, "READ_EXTERNAL_STORAGE was not granted. :/", Snackbar.LENGTH_LONG).show()
                }
            }

    private fun loadFromAssets() {
        ThreeHundredSixtyPlayerActivity.Builder
                .with(this)
                .uri(parseAssetFile("large.jpg"))
                .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE)
                .interactiveMode(ThreeHundredSixtyPlayer.INTERACTIVE_MODE_TOUCH)
                .startActivity()
        finish()
    }
}