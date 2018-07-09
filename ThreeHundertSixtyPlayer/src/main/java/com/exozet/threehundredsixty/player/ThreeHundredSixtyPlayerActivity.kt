package com.exozet.threehundredsixty.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer.Companion.INTERACTIVE_MODE_MOTION_WITH_TOUCH
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer.Companion.PROJECTION_MODE_SPHERE
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer.Companion.SHOW_CONTROLS
import kotlinx.android.synthetic.main.activity_threehundredsixty_player.*
import java.lang.ref.WeakReference


class ThreeHundredSixtyPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threehundredsixty_player)

        val file: Uri? = intent?.extras?.getParcelable(Uri::class.java.canonicalName)
        if (file == null) {
            finish()
            return
        }

        threeHundredSixtyView.uri = file

        threeHundredSixtyView.showControls = intent?.extras?.getBoolean(SHOW_CONTROLS) ?: false

        threeHundredSixtyView.projectionMode = intent?.extras?.getInt(ProjectionMode::class.java.canonicalName)
                ?: PROJECTION_MODE_SPHERE

        threeHundredSixtyView.interactionMode = intent?.extras?.getInt(InteractionMode::class.java.canonicalName)
                ?: INTERACTIVE_MODE_MOTION_WITH_TOUCH
    }

    class Builder private constructor() {

        private lateinit var context: WeakReference<Context>

        private var uri: Uri? = null

        private var showControls: Boolean = false

        @InteractionMode
        private var interactiveMode: Int = INTERACTIVE_MODE_MOTION_WITH_TOUCH

        @ProjectionMode
        private var projectionMode: Int = PROJECTION_MODE_SPHERE

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

        fun startActivity() = context.get()!!.startActivity(Intent(context.get(), ThreeHundredSixtyPlayerActivity::class.java)
                .apply {
                    putExtra(Uri::class.java.canonicalName, uri)
                    putExtra(ProjectionMode::class.java.canonicalName, projectionMode)
                    putExtra(InteractionMode::class.java.canonicalName, interactiveMode)
                    putExtra(SHOW_CONTROLS, showControls)
                })

        companion object {
            fun with(context: Context): Builder = Builder().also { it.context = WeakReference(context) }
        }
    }
}