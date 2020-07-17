package com.example.moviedemo

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import timber.log.Timber
import java.io.File



@BindingAdapter("bindAvatarProfile")
fun bindAvatarProfileFirstTime(imageView: ImageView, path: String?){


    Timber.i("Load $path")
    path?.let {

        val imgFile= File(path)
        if (imgFile.exists()){
            imageView.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
        }

    }


}