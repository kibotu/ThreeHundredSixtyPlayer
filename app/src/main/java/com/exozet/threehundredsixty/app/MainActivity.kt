package com.exozet.threehundredsixty.app

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayerActivity
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        loadFromAssets()

        loadFromSDCard()
    }

    private fun loadFromSDCard() =
            RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe { granted ->
                if (granted) { // Always true pre-M
                    startActivity(Intent(this, ThreeHundredSixtyPlayerActivity::class.java).apply {
                        putExtra(ThreeHundredSixtyPlayerActivity.FILE_PATH, "/${Environment.getExternalStorageDirectory()}/DCIM/large.jpg")
                    })
                    finish()
                } else {
                    Snackbar.make(root, "READ_EXTERNAL_STORAGE was not granted. :/", Snackbar.LENGTH_LONG).show()
                }
            }

    private fun loadFromAssets() {
        startActivity(Intent(this, ThreeHundredSixtyPlayerActivity::class.java).apply {
            putExtra(ThreeHundredSixtyPlayerActivity.ASSET_FILE_PATH, "large.jpg")
        })
        finish()
    }
}