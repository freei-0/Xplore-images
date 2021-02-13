package demo.freei.xploreimages.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    var id: String,
    var width: Int,
    var height: Int,
    val color: String?,
    val blur_hash: String?,
    val description: String?,
    val urls: Urls?,
    val links: Links
) : Parcelable {

    @Parcelize
    data class Urls(
        val full: String,
        val raw: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class Links(
        val download: String,
        val downloadLocation: String,
        val html: String,
        val self: String
    ) : Parcelable
}


