package com.exozet.threehundredsixty.player

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asha.vrlib.MDVRLibrary
import com.asha.vrlib.texture.MD360BitmapTexture
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_threehundredsixty_player.*


internal class ThreeHundredSixtyPlayerActivity : AppCompatActivity() {

    var vrLibrary: MDVRLibrary? = null

    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threehundredsixty_player)

        busy()

        // we have no data
        val file = intent?.extras?.getString(Uri::class.java.canonicalName)
        if (isEmpty(file))
            finish()

        uri = Uri.parse(file)

        initVRLibrary()

        vrLibrary?.switchDisplayMode(this, intent?.extras?.getInt(MDVRLibrary::class.java.canonicalName)
                ?: MDVRLibrary.PROJECTION_MODE_SPHERE)

        motionSwitch.setOnCheckedChangeListener { _, isChecked -> vrLibrary?.switchInteractiveMode(this, if (isChecked) MDVRLibrary.INTERACTIVE_MODE_MOTION_WITH_TOUCH else MDVRLibrary.INTERACTIVE_MODE_TOUCH) }

        vrLibrary?.notifyPlayerChanged()
    }

    private fun initVRLibrary() {
        // new instance
        vrLibrary = MDVRLibrary.with(this)
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_TOUCH)
                .pinchEnabled(true)
                .asBitmap { callback -> loadImage(uri, callback) }
                .build(glView)
    }

    private var target: Target? = null

    private fun loadImage(uri: Uri?, callback: MD360BitmapTexture.Callback) {
        target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.d(TAG, "loaded image, size:" + bitmap?.width + "," + bitmap?.height)

                // notify if size changed
                vrLibrary?.onTextureResize(bitmap?.width?.toFloat() ?: 0f, bitmap?.height?.toFloat()
                        ?: 0f)

                // texture
                callback.texture(bitmap)
                cancelBusy()
            }
        }

        Log.d(TAG, "Load $uri with GL_MAX_TEXTURE_SIZE size:${callback.maxTextureSize}")

        Picasso.with(applicationContext)
                .load(uri ?: return)
                .resize(callback.maxTextureSize, callback.maxTextureSize)
                .onlyScaleDown()
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(target)
    }

    private val TAG = this::class.java.simpleName

//    public interface @ProjectionMode{}

    private fun cancelBusy() {
        progress.visibility = View.GONE
    }

    private fun busy() {
        progress.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        vrLibrary?.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        vrLibrary?.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        vrLibrary?.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        vrLibrary?.onOrientationChanged(this)
    }
}

