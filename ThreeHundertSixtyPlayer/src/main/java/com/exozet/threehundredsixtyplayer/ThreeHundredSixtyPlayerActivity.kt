package com.exozet.threehundredsixtyplayer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixtyplayer.ThreeHundredSixtyPlayer.Companion.SHOW_CONTROLS
import kotlinx.android.synthetic.main.activity_threehundredsixty_player.*
import java.lang.ref.WeakReference


class ThreeHundredSixtyPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threehundredsixty_player)

        var sample1 = "interior_example.jpg"
        var sample2 = "equirectangular.jpg"
        var current = sample2

        var image1: Bitmap? = null
        var image2: Bitmap? = null
        var current2: Bitmap?

        sample1.parseAssetFile().loadImage(this) {
            image1 = it
        }

        sample2.parseAssetFile().loadImage(this) {
            image2 = it
        }

        next.setOnClickListener {

            if (current == sample1) {
                current = sample2
                current2 = image2
            } else {
                current = sample1
                current2 = image1
            }

            Log.v("ThreeHundredSixty", "current=$current")

            threeHundredSixtyView.bitmap = current2
        }

        if (intent?.extras?.containsKey(Uri::class.java.canonicalName) == true)
            threeHundredSixtyView.uri = intent?.extras?.getParcelable(Uri::class.java.canonicalName)

        if (intent?.extras?.containsKey(SHOW_CONTROLS) == true)
            intent?.extras?.getBoolean(SHOW_CONTROLS)?.let {
                threeHundredSixtyView.showControls = it
            }

        if (intent?.extras?.containsKey(ProjectionMode::class.java.canonicalName) == true)
            intent?.extras?.getInt(ProjectionMode::class.java.canonicalName)?.let {
                threeHundredSixtyView.projectionMode = it
            }

        if (intent?.extras?.containsKey(InteractionMode::class.java.canonicalName) == true)
            intent?.extras?.getInt(InteractionMode::class.java.canonicalName)?.let {
                threeHundredSixtyView.interactionMode = it
            }
    }

    class Builder private constructor() {

        private lateinit var context: WeakReference<Context>

        private var uri: Uri? = null

        private var showControls: Boolean? = null

        @InteractionMode
        private var interactiveMode: Int? = null

        @ProjectionMode
        private var projectionMode: Int? = null

        fun showControls(showControls: Boolean = true): Builder {
            this.showControls = showControls
            return this
        }

        fun uri(uri: Uri): Builder {
            this.uri = uri
            return this
        }

        fun interactiveMode(@InteractionMode interactiveMode: Int): Builder {
            this.interactiveMode = interactiveMode
            return this
        }

        fun projectMode(@ProjectionMode projectionMode: Int): Builder {
            this.projectionMode = projectionMode
            return this
        }

        fun startActivity() =
                context.get()!!.startActivity(Intent(context.get(), ThreeHundredSixtyPlayerActivity::class.java)
                        .apply {
                            uri?.let { putExtra(Uri::class.java.canonicalName, it) }
                            projectionMode?.let { putExtra(ProjectionMode::class.java.canonicalName, it) }
                            interactiveMode?.let { putExtra(InteractionMode::class.java.canonicalName, it) }
                            showControls?.let { putExtra(SHOW_CONTROLS, it) }
                        })

        companion object {
            fun with(context: Context): Builder = Builder().also { it.context = WeakReference(context) }
        }
    }
}