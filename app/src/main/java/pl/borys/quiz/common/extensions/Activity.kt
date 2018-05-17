package pl.borys.quiz.common.extensions

import android.app.Activity
import android.os.Parcelable
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
fun <T : Parcelable> Activity.extraParcelable(key: String): Lazy<T> {
    return lazy { intent.getParcelableExtra<T>(key) }
}


@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Activity.extra(key: String): Lazy<T> {
    return lazy { intent.getSerializableExtra(key) as T }
}