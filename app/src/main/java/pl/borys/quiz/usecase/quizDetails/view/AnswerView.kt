package pl.borys.quiz.usecase.quizDetails.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.find
import pl.borys.quiz.model.dto.Answer

class AnswerView(context: Context?) : LinearLayout(context) {

    init {
        val inflater: LayoutInflater? = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater?.inflate(R.layout.answer_item, this, true)
    }

    private val answerView: TextView by find(R.id.answer)

    fun bind(answer: Answer){
        answerView.text = answer.text
    }
}