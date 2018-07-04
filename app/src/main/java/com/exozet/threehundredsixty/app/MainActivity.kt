package com.exozet.threehundredsixty.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, ThreeHundredSixtyPlayerActivity::class.java).apply {
            putExtra("ASSET_FILE_NAME", "large.jpg")
        }
        startActivity(intent)
        finish()
    }
}