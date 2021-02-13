package demo.freei.xploreimages.imagedetail

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import demo.freei.xploreimages.R
import demo.freei.xploreimages.databinding.FragmentImageDetailBinding
import demo.freei.xploreimages.models.Image
import demo.freei.xploreimages.util.toast
import demo.freei.xploreimages.util.viewBinding
import java.io.File


class ImageDetailFragment : Fragment(R.layout.fragment_image_detail) {

    private val binding by viewBinding(FragmentImageDetailBinding::bind)
    private val args by navArgs<ImageDetailFragmentArgs>()

    companion object {
        const val MEDIA_TYPE_IMAGE = "image/jpeg"
        const val RC_PERMISSIONS = 3459
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image: Image = args.image
        binding.apply {
            Glide.with(view)
                .load(image.urls?.regular)
                .error(android.R.drawable.stat_notify_error)
                .into(ivImageDetail)

            ivImageDetailSave.setOnClickListener {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    downloadImage(image.links.download)
                } else {
                    requestLegacyWritePermissionOptional()
                }
            }
        }
    }

    private fun requestLegacyWritePermissionOptional() {
        context?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                downloadImage(args.image.links.download)
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    RC_PERMISSIONS
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RC_PERMISSIONS) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        downloadImage(args.image.links.download)
                        return
                    }
                }
            }
        }
    }

    private fun downloadImage(
        downloadUrlOfImage: String,
        filename: String = "IMG_${System.currentTimeMillis()}"
    ) {
        try {
            val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType(MEDIA_TYPE_IMAGE) // Your file type. You can use this code to download other file types also.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )
            dm.enqueue(request)
            context?.toast("Image download started...")
        } catch (e: Exception) {
            context?.toast("Image download failed.")
        }
    }

}