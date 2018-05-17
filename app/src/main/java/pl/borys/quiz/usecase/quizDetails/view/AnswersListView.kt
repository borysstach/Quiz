package pl.borys.quiz.usecase.quizDetails.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import pl.borys.quiz.model.dto.Answer

class AnswersListView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var onAnswerClickListener: (Answer) -> Unit = {}

    init {
        orientation = LinearLayout.VERTICAL
    }

    fun bind(answers: List<Answer>) {
        answers
                .sortedBy { it.order }
                .map { answer ->
                    AnswerView(context).apply {
                        bind(answer)
                        setOnClickListener { answerClicked(answer) }
                    }
                }
                .forEach {
                    addView(it)
                }

    }

    fun setOnAnswerClickListener(listener: (Answer) -> Unit) {
        onAnswerClickListener = listener
    }

    private fun answerClicked(answer: Answer) {
        onAnswerClickListener(answer)
    }


}