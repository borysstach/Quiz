package pl.borys.quiz.usecase.quizDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.extra
import pl.borys.quiz.model.dto.QuizId

class QuizDetailsActivity : AppCompatActivity() {

    private val quizId: QuizId by extra(QUIZ_ID_EXTRA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_details_activity)
    }

    companion object {
        private const val QUIZ_ID_EXTRA = "quizIdExtra"

        fun start(context: Context, quizId: QuizId) {
            context.startActivity(getStartIntent(context, quizId))
        }

        fun getStartIntent(context: Context, quizId: QuizId) =
                Intent(context, QuizDetailsActivity::class.java).apply {
                    putExtra(QUIZ_ID_EXTRA, quizId)
                }
    }
}