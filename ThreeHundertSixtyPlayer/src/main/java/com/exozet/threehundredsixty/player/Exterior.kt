package com.exozet.threehundredsixty.player

data class Exterior(
        var id: Int? = 0,
        var adId: String? = "",
        var state: Any? = Any(),
        var imageMedia: ImageMedia? = ImageMedia(),
        var createdAt: String? = "",
        var meta: Meta? = Meta()
)

data class ImageMedia(
        var id: Int? = 0,
        var publicUrls: PublicUrls? = PublicUrls()
)

data class Meta(
        var key: String? = ""
)

data class PublicUrls(
        var default_small: String? = "",
        var default_admin_list: String? = "",
        var default_admin_thumbnail: String? = "",
        var default_admin_hd: String? = "",
        var reference: String? = ""
)