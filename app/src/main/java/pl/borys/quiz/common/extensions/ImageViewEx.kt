package pl.borys.quiz.common.extensions

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pl.borys.quiz.common.GlideApp
import pl.borys.quiz.model.dto.Photo

fun ImageView.loadWithGlide(photo: Photo){
    GlideApp.with(this)
            .load(photo.url.toUri())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}