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

    private var instance: ThreeHundredSixtyPlayer = this

    private lateinit var context: WeakReference<Context>

    private var uri: Uri? = null

    @ProjectionMode
    private var projectionMode: Int = PROJECTION_MODE_SPHERE

    fun projectMode(@ProjectionMode projectionMode: Int): ThreeHundredSixtyPlayer {
        this.projectionMode = projectionMode
        return this
    }

    fun assetFile(fileName: String): ThreeHundredSixtyPlayer {
        uri = Uri.parse("file:///android_asset/$fileName")
        return this
    }

    fun internalStorageFile(fileName: String): ThreeHundredSixtyPlayer {
        uri = Uri.parse("${context.get()?.filesDir?.absolutePath}/$fileName")
        return this
    }

    fun externalStorageFile(fileName: String): ThreeHundredSixtyPlayer {
        uri = Uri.parse("${Environment.getExternalStorageDirectory()}/$fileName")
        return this
    }

    fun file(fileName: String): ThreeHundredSixtyPlayer {
        uri = Uri.fromFile(File(fileName))
        return this
    }

    fun startActivity() = context.get()!!.startActivity(Intent(context.get(), ThreeHundredSixtyPlayerActivity::class.java)
            .apply {
                putExtra(Uri::class.java.canonicalName, uri.toString())
                putExtra(MDVRLibrary::class.java.canonicalName, projectionMode)
            })

    companion object {

        fun with(context: Context): ThreeHundredSixtyPlayer = ThreeHundredSixtyPlayer().also { it.context = WeakReference(context) }

        const val PROJECTION_MODE_SPHERE = MDVRLibrary.PROJECTION_MODE_SPHERE
        const val PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL = MDVRLibrary.PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL
        const val PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL = MDVRLibrary.PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL

        @IntDef(PROJECTION_MODE_SPHERE, PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL, PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ProjectionMode
    }
}