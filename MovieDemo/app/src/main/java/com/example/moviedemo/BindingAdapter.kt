package com.example.moviedemo

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviedemo.repository.Repository
import timber.log.Timber
import java.io.File


@BindingAdapter("bindAvatarProfile")
fun bindAvatarProfile(imageView: ImageView, path: String?) {

    Timber.i("Bind avatar $path")
    path?.let {
        val imgFile = File(path)
        if (imgFile.exists()) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
            Timber.i("Finished Bind avatar $path")

        }


    }

}
@BindingAdapter("imgURL")
fun bindImagePoster(imgView: ImageView, imgURL: String?) {

    imgURL?.let {
        val url =Repository.getUrLImage(imgURL)
        Log.d("Binding image got", url)

        val imgURi = imgURL.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context).load(url).apply(
            RequestOptions()
            .placeholder(R.drawable.loading_animation)
//            .error(R.drawable.ic_broken_image)
        ).into(imgView)
    }
}
