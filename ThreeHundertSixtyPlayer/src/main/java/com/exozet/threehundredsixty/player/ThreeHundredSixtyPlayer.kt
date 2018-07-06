package com.exozet.threehundredsixty.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.annotation.IntDef
import com.asha.vrlib.MDVRLibrary
import java.io.File
import java.lang.ref.WeakReference


class ThreeHundredSixtyPlayer private constructor() {

    private lateinit var context: WeakReference<Context>

    private var uri: Uri? = null

    private var motion: Boolean = false

    @ProjectionMode
    private var projectionMode: Int = PROJECTION_MODE_SPHERE

    fun motion(motion: Boolean = true): ThreeHundredSixtyPlayer {
        this.motion = motion
        return this
    }

    fun projectMode(@ProjectionMode projectionMode: Int): ThreeHundredSixtyPlayer {
        this.projectionMode = projectionMode
        return this
    }

    fun assetFile(file: String): ThreeHundredSixtyPlayer {
        uri = Uri.parse("file:///android_asset/$file")
        return this
    }

    fun internalStorageFile(file: String): ThreeHundredSixtyPlayer {
        uri = Uri.parse("${context.get()?.filesDir?.absolutePath}/$file")
        return this
    }

    fun externalStorageFile(file: String): ThreeHundredSixtyPlayer {
        uri = Uri.parse("${Environment.getExternalStorageDirectory()}/$file")
        return this
    }

    fun file(file: String): ThreeHundredSixtyPlayer {
        uri = Uri.fromFile(File(file))
        return this
    }

    fun startActivity() = context.get()!!.startActivity(Intent(context.get(), ThreeHundredSixtyPlayerActivity::class.java)
            .apply {
                putExtra(Uri::class.java.canonicalName, uri.toString())
                putExtra(MDVRLibrary::class.java.canonicalName, projectionMode)
                putExtra(MOTION, motion)
            })

    companion object {

        fun with(context: Context): ThreeHundredSixtyPlayer = ThreeHundredSixtyPlayer().also { it.context = WeakReference(context) }

        const val PROJECTION_MODE_SPHERE = MDVRLibrary.PROJECTION_MODE_SPHERE
        const val PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL = MDVRLibrary.PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL
        const val PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL = MDVRLibrary.PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL

        internal val MOTION = "MOTION"

        @IntDef(PROJECTION_MODE_SPHERE, PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL, PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ProjectionMode
    }
}