package com.exozet.threehundredsixty.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.isEmpty
import androidx.appcompat.app.AppCompatActivity
import com.exozet.parseAssetFile
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer.Companion.INTERACTIVE_MODE_MOTION_WITH_TOUCH
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer.Companion.PROJECTION_MODE_SPHERE
import kotlinx.android.synthetic.main.activity_threehundredsixty_player.*
import java.lang.ref.WeakReference


internal class ThreeHundredSixtyPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threehundredsixty_player)

        val file = intent?.extras?.getString(Uri::class.java.canonicalName)
        if (isEmpty(file)) {
            finish()
            return
        }

        threeHundredSixtyView.uri = parseAssetFile(file!!)

        threeHundredSixtyView.projectionMode = intent?.extras?.getInt(ProjectionMode::class.java.canonicalName)
                ?: PROJECTION_MODE_SPHERE

        threeHundredSixtyView.interactionMode = intent?.extras?.getInt(InteractionMode::class.java.canonicalName)
                ?: INTERACTIVE_MODE_MOTION_WITH_TOUCH
    }

    class Builder private constructor() {

        private lateinit var context: WeakReference<Context>

        private var uri: Uri? = null

        @InteractionMode
        private var interactiveMode: Int = INTERACTIVE_MODE_MOTION_WITH_TOUCH

        @ProjectionMode
        private var projectionMode: Int = PROJECTION_MODE_SPHERE

        fun interactiveMode(@InteractionMode interactiveMode: Int): Builder {
            this.interactiveMode = interactiveMode
            return this
        }

        fun projectMode(@ProjectionMode projectionMode: Int): Builder {
            this.projectionMode = projectionMode
            return this
        }

        fun startActivity() = context.get()!!.startActivity(Intent(context.get(), ThreeHundredSixtyPlayerActivity::class.java)
                .apply {
                    putExtra(Uri::class.java.canonicalName, uri.toString())
                    putExtra(ProjectionMode::class.java.canonicalName, projectionMode)
                    putExtra(InteractionMode::class.java.canonicalName, interactiveMode)
                })

        companion object {
            fun with(context: Context): Builder = Builder().also { it.context = WeakReference(context) }
        }
    }
}