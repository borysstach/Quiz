package pl.borys.quiz.common.extensions

import android.support.annotation.IdRes
import android.view.View


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setVisibility(visible: Boolean) {
    if (visible) show() else hide()
}

@Suppress("UNCHECKED_CAST")
fun <T : View> View.find(@IdRes id: Int): Lazy<T> {
    return lazy { findViewById<T>(id) }
}