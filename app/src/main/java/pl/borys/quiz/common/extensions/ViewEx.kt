package pl.borys.quiz.common.extensions

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