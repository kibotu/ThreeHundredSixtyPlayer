package com.exozet.threehundredsixty.app

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixty.player.*
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // internal=/data/user/0/com.exozet.threehundertsixty.app/files/myfile.jpg
        // assets= file:///android_asset/myfile.jpg
        // external=/storage/emulated/0/myfile.jpg
        // file=file:///myfile.jpg
        Log.v("MainActivity",
                "internal=${parseInternalStorageFile(this, "myfile.jpg")}" +
                        " assets= ${parseAssetFile("myfile.jpg")}" +
                        " external=${parseExternalStorageFile("myfile.jpg")}" +
                        " file=${parseFile("myfile.jpg")}" +
                        "")

        loadFromAssets()
//        load()
    }

    private fun load() =
            RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe { granted ->
                if (granted) { // Always true pre-M
                    ThreeHundredSixtyPlayerActivity.Builder
                            .with(this)
                            .uri(parseExternalStorageFile("DCIM/large.jpg"))
                            .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE)
                            .interactiveMode(ThreeHundredSixtyPlayer.INTERACTIVE_MODE_TOUCH)
                            .showControls()
                            .startActivity()
                    finish()
                } else {
                    Snackbar.make(root, "READ_EXTERNAL_STORAGE was not granted. :/", Snackbar.LENGTH_LONG).show()
                }
            }

    private fun loadFromAssets() {
        ThreeHundredSixtyPlayerActivity.Builder
                .with(this)
                .uri(parseAssetFile("interior_example.jpg"))
                .showControls()
                .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE)
                .interactiveMode(ThreeHundredSixtyPlayer.INTERACTIVE_MODE_TOUCH)
                .startActivity()
        finish()
    }
}