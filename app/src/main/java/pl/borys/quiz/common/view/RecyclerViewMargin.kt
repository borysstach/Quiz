package pl.borys.quiz.common.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerViewMargin(
        private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildLayoutPosition(view)
        outRect.right = margin
        outRect.bottom = margin
        outRect.left = margin
        if (position == 0) {
            outRect.top = margin
        }
    }
}
