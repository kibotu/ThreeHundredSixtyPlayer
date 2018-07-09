# ThreeHundredSixtyPlayer [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17) [![Gradle Version](https://img.shields.io/badge/gradle-4.8.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.2.51-green.svg)](https://kotlinlang.org/)  

Native OpenGLES 2.0 360 Degree Player

[![Screenshot](https://git.exozet.com/mobile-de/POC/android-360-player/raw/master/demo.gif)](https://git.exozet.com/mobile-de/POC/android-360-player/raw/master/demo.gif)

# How to use

## As view

    <?xml version="1.0" encoding="utf-8"?>
    <androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/root"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    
        <com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
            android:id="@+id/threeHundredSixtyView"
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    
    </androidx.coordinatorlayout.widget.CoordinatorLayout>
    
    private fun startThreeHundredSixtyPlayer(filename: String) = with(threeHundredSixtyView) {
        uri = parseAssetFile(filename)
        projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE
        interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH
        showControls = true
    }

## As Standalone Activity

Start 360 Degree Activity by passing bitmap file path for or an  bitmap
               
       ThreeHundredSixtyPlayerActivity.Builder
                .with(this)
                // (Note you require [READ_EXTERNAL_STORAGE Permission](https://developer.android.com/reference/android/Manifest.permission.html#READ_EXTERNAL_STORAGE))
                //.uri(parseInternalStorageFile("large.jpg"))
                .uri(parseAssetFile("large.jpg"))
                //.uri(parseExternalStorageFile("large.jpg"))
                //.uri(parseFile("large.jpg")) 
                .showControls() // default: false 
                .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE) // default: PROJECTION_MODE_SPHERE
                .interactiveMode(ThreeHundredSixtyPlayer.INTERACTIVE_MODE_TOUCH) // default: INTERACTIVE_MODE_MOTION_WITH_TOUCH
                .startActivity()

## Projection Modes

#### [Equirectangular](https://developers.theta360.com/en/docs/introduction/)

![Equirectangular](https://developers.theta360.com/intl/common/img/equirectangular.bmp)

    ThreeHundredSixtyPlayer.with(this)
        .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE) // default

#### [Dual-Fisheye](https://developers.theta360.com/en/docs/introduction/)

![Dual-Fisheye](https://developers.theta360.com/intl/common/img/dualfisheye.bmp)

    ThreeHundredSixtyPlayer.with(this)
        .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL) // or veritcal: PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL

## Interaction Modes

* INTERACTIVE_MODE_TOUCH - Swipe Gestures
* INTERACTIVE_MODE_MOTION - Motion Sensors
* INTERACTIVE_MODE_MOTION_WITH_TOUCH - Both combined

# How to install (tbd)

Atm only as module
    
    dependencies {
        api project(':ThreeHundertSixtyPlayer')
    }
    
# GL_MAX_TEXTURE_SIZE

Player will log max supported texture size during surface view creation. Look out for **ThreeHundredSixtyPlayer**, e.g:
    
    ThreeHundredSixtyPlayer: GL_MAX_TEXTURE_SIZE 8192x8192    
    
# Changelog

* Supports showing controls
* Supports motion configuration
* Supports motion sensors
* Supports swiping 
* Supports pinch zoom
* Supports orientation changes
* Supports automatically down scaling of images to GL_MAX_TEXTURE_SIZE
* Supports equirectangular textures 
* Supports dual-fish-eye-horizontal, dual-fish-eye-vertical textures 
* Supports loading indicator
* Supports loading from asset-folder
* Supports loading from internal storage
* Supports loading from external storage
* Supports loading from file 

## Contributors

[Jan Rabe](jan.rabe@exozet.com)