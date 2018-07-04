# ThreeHundredSixtyPlayer [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17) [![Gradle Version](https://img.shields.io/badge/gradle-4.8.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.2.50-green.svg)](https://kotlinlang.org/)  

# How to use

### Start 360 Degree Activity by equirectangular bitmap

#### Load from asset folder 

    startActivity(Intent(this, ThreeHundredSixtyPlayerActivity::class.java).apply {
        putExtra("ASSET_FILE_NAME", "large.jpg")
    })

#### Load from file path (Note you require [READ_EXTERNAL_STORAGE Permission](https://developer.android.com/reference/android/Manifest.permission.html#READ_EXTERNAL_STORAGE)) 
    
    startActivity(Intent(this, ThreeHundredSixtyPlayerActivity::class.java).apply {
        putExtra(ThreeHundredSixtyPlayerActivity.FILE_PATH, "/${Environment.getExternalStorageDirectory()}/DCIM/large.jpg")
    })
     
# How to install (tbd)

Atm only as module
    
    dependencies {
        api project(':ThreeHundertSixtyPlayer')
    }
    
# GL_MAX_TEXTURE_SIZE

Player will log max supported texture size during surface view creation. Look out for **ThreeHundredSixtyPlayer**, e.g:
    
    ThreeHundredSixtyPlayer: GL_MAX_TEXTURE_SIZE 8192x8192

# Resize images

    brew install imagemagick
    mogrify -resize 8192x -quality 100 "large.jpg" 
    
    
## Contributors

[Jan Rabe](jan.rabe@exozet.com)