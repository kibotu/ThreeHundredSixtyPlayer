# ThreeHundredSixtyPlayer [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17) [![Gradle Version](https://img.shields.io/badge/gradle-4.8.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.2.50-green.svg)](https://kotlinlang.org/)  

# How to use

### Start 360 Degree Activity by equirectangular bitmap

#### Load from asset folder 

    startActivity(Intent(this, ThreeHundredSixtyPlayerActivity::class.java).apply {
        putExtra("ASSET_FILE_NAME", "large.jpg")
    })

#### Load from file path (Note you require [READ_EXTERNAL_STORAGE Permission](https://developer.android.com/reference/android/Manifest.permission.html#READ_EXTERNAL_STORAGE)) 
    
    startActivity(Intent(this, ThreeHundredSixtyPlayerActivity::class.java).apply {
        putExtra("ASSET_FILE_NAME", "/sdcard/DCIM/large.jpg")
    })
     
# How to install

# Resize images

    brew install imagemagick
    mogrify -resize 8192x -quality 100 "large.jpg" 