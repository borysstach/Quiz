package pl.borys.quiz.common.view

import android.support.design.widget.CoordinatorLayout
import android.view.MotionEvent
import android.view.View
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.inflate
import pl.borys.quiz.common.extensions.show

fun CoordinatorLayout.showLoader() {
    removeView(findViewById(R.id.fullscreen_loader))
    addView(inflate(R.layout.fullscreen_loader).apply {
        show()
        setOnTouchListener { _: View, _: MotionEvent -> true }
    })
}


fun CoordinatorLayout.hideLoader() {
    removeView(findViewById(R.id.fullscreen_loader))
}
