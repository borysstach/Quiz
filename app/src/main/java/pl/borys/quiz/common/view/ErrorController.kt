package pl.borys.quiz.common.view

import android.support.design.widget.CoordinatorLayout
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.inflate
import pl.borys.quiz.common.extensions.show

fun CoordinatorLayout.showError(refresh: () -> Unit) {
    removeView(findViewById(R.id.fullscreenError))
    addView(inflate(R.layout.fullscreen_error).apply {
        show()
        setOnTouchListener { _: View, _: MotionEvent -> true }

        val button: Button = this.findViewById(R.id.refreshButton)
        button.setOnClickListener {
            refresh()
            removeView(findViewById(R.id.fullscreenError))
        }
    })
}