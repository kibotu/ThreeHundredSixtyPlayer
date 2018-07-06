# ThreeHundredSixtyPlayer [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17) [![Gradle Version](https://img.shields.io/badge/gradle-4.8.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.2.51-green.svg)](https://kotlinlang.org/)  

Native OpenGLES 2.0 360 Degree Player

[![Screenshot](https://git.exozet.com/mobile-de/POC/android-360-player/raw/master/demo.gif)](https://git.exozet.com/mobile-de/POC/android-360-player/raw/master/demo.gif)

# How to use

Start 360 Degree Activity by passing bitmap file path for or an  bitmap

       ThreeHundredSixtyPlayer
               .with(this)
               // .projectMode(ThreeHundredSixtyPlayer.PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL)
               // .internalStorageFile("/cache/large.jpg")
               // .assetFile("large.png")
               // (Note you require [READ_EXTERNAL_STORAGE Permission](https://developer.android.com/reference/android/Manifest.permission.html#READ_EXTERNAL_STORAGE))
               // .file("${Environment.getExternalStorageDirectory()}/DCIM/large.jpg") 
               .externalStorageFile("/DCIM/large.jpg")
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

# How to install (tbd)

Atm only as module
    
    dependencies {
        api project(':ThreeHundertSixtyPlayer')
    }
    
# GL_MAX_TEXTURE_SIZE

Player will log max supported texture size during surface view creation. Look out for **ThreeHundredSixtyPlayer**, e.g:
    
    ThreeHundredSixtyPlayer: GL_MAX_TEXTURE_SIZE 8192x8192    
    
# Changelog

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