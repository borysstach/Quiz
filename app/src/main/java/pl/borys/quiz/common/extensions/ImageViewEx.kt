package pl.borys.quiz.common.extensions

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pl.borys.quiz.R
import pl.borys.quiz.common.GlideApp
import pl.borys.quiz.model.dto.Photo

fun ImageView.load(photo: Photo){
    GlideApp.with(this)
            .load(photo.url.toUri())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}

fun ImageView.loadWithPlaceholder(
        photo: Photo,
        @DrawableRes placeholder: Int = R.drawable.quiz_placeholder
){
    GlideApp.with(this)
            .load(photo.url.toUri())
            .dontAnimate()
            .placeholder(placeholder)
            .error(placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}