package com.exozet.threehundredsixty.player

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.asha.vrlib.MDVRLibrary
import com.asha.vrlib.texture.MD360BitmapTexture
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.threehundredsixty_view.view.*
import java.util.*


class ThreeHundredSixtyPlayer @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val TAG by lazy { "${this::class.java.simpleName}:$uuid" }

    private val uuid: String by lazy { UUID.randomUUID().toString().take(8) }

    private var vrLibrary: MDVRLibrary? = null

    var debug = true

    private fun log(message: String) {
        if (debug)
            Log.d(TAG, message)
    }

    var uri: Uri? = null
        set(value) {
            if (field == value)
                return
            field = value
            vrLibrary?.notifyPlayerChanged()
        }

    @ProjectionMode
    var projectionMode: Int = PROJECTION_MODE_SPHERE
        set(value) {
            field = value
            vrLibrary?.switchDisplayMode(context, projectionMode)
            vrLibrary?.notifyPlayerChanged()
        }

    @InteractionMode
    var interactionMode: Int = INTERACTIVE_MODE_TOUCH
        set(value) {
            field = value
            motionSwitch.isChecked = value == INTERACTIVE_MODE_MOTION_WITH_TOUCH
            vrLibrary?.switchInteractiveMode(context, value)
            vrLibrary?.notifyPlayerChanged()
        }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.threehundredsixty_view, this, true)
    }

    private fun initVRLibrary() {
        vrLibrary = MDVRLibrary.with(context)
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(interactionMode)
                .pinchEnabled(true)
                .asBitmap { callback -> loadImage(uri, callback) }
                .build(glView)
    }

    private fun onCreate() {
        initVRLibrary()
        motionSwitch.setOnCheckedChangeListener { _, isChecked -> interactionMode = if (isChecked) INTERACTIVE_MODE_MOTION_WITH_TOUCH else INTERACTIVE_MODE_TOUCH }
    }

    private fun busy() {
        progress.visibility = View.VISIBLE
    }

    private fun cancelBusy() {
        progress.visibility = View.GONE
    }

    private fun onResume() {
        vrLibrary?.onResume(context)
    }

    private fun onPause() {
        vrLibrary?.onPause(context)
    }

    private fun onDestroy() {
        vrLibrary?.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        vrLibrary?.onOrientationChanged(context)
    }

    private var target: Target? = null

    private fun loadImage(uri: Uri?, callback: MD360BitmapTexture.Callback) {
        target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                log("loaded image, size:" + bitmap?.width + "," + bitmap?.height)

                // notify if size changed
                vrLibrary?.onTextureResize(bitmap?.width?.toFloat() ?: 0f, bitmap?.height?.toFloat()
                        ?: 0f)

                // texture
                callback.texture(bitmap)
                cancelBusy()
            }
        }

        log("Load $uri with GL_MAX_TEXTURE_SIZE size:${callback.maxTextureSize}")

        Picasso.with(context)
                .load(uri ?: return)
                .resize(callback.maxTextureSize, callback.maxTextureSize)
                .onlyScaleDown()
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(target)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        busy()
        onCreate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onDestroy()
        cancelBusy()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) onResume() else onPause()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) onResume() else onPause()
    }

    companion object {
        const val PROJECTION_MODE_SPHERE = MDVRLibrary.PROJECTION_MODE_SPHERE
        const val PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL = MDVRLibrary.PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL
        const val PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL = MDVRLibrary.PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL
        const val INTERACTIVE_MODE_TOUCH = MDVRLibrary.INTERACTIVE_MODE_TOUCH
        const val INTERACTIVE_MODE_MOTION = MDVRLibrary.INTERACTIVE_MODE_MOTION
        const val INTERACTIVE_MODE_MOTION_WITH_TOUCH = MDVRLibrary.INTERACTIVE_MODE_MOTION_WITH_TOUCH
    }
}