package com.exozet.threehundredsixtyplayer

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File


fun String.parseAssetFile(): Uri = Uri.parse("file:///android_asset/$this")

fun String.parseInternalStorageFile(context: Context): Uri = Uri.parse("${context.filesDir.absolutePath}/$this")

fun String.parseExternalStorageFile(): Uri = Uri.parse("${Environment.getExternalStorageDirectory()}/$this")

fun String.parseFile(): Uri = Uri.fromFile(File(this))

fun Uri.loadImage(context: Context, block: (bitmap: Bitmap?) -> Unit) = Glide.with(context)
        .asBitmap()
        .load(this)
        .apply(
                RequestOptions
                        .fitCenterTransform()
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .skipMemoryCache(false)
                        .override(2048, 2048)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                block(resource)
            }
        })