package pl.borys.quiz.usecase.quizDetails.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import org.greenrobot.eventbus.EventBus
import pl.borys.quiz.model.dto.Answer
import pl.borys.quiz.usecase.quizDetails.events.AnswerClickedEvent

class AnswersListView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        orientation = LinearLayout.VERTICAL
    }

    fun bind(answers: List<Answer>) {
        removeAllViews()
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

    private fun answerClicked(answer: Answer) {
        EventBus.getDefault().post(AnswerClickedEvent(answer))
    }


}