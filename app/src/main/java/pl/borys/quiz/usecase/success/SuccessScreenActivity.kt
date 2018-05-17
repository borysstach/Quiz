package pl.borys.quiz.usecase.success

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.extra
import pl.borys.quiz.common.extensions.extraParcelable
import pl.borys.quiz.model.dto.QuizId
import pl.borys.quiz.usecase.success.dto.AnswersWrapper
import pl.borys.quiz.usecase.success.dto.SuccessScreenStartBundle

class SuccessScreenActivity : AppCompatActivity() {

    private val quizId: QuizId by extra(QUIZ_ID_EXTRA)
    private val answers: AnswersWrapper by extraParcelable(QUIZ_ID_EXTRA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_activity)
    }

    companion object {
        private const val ANSWERS_EXTRA = "successAnswersExtra"
        private const val QUIZ_ID_EXTRA = "successQuizIdExtra"

        fun start(context: Context, bundle: SuccessScreenStartBundle) {
            context.startActivity(getIntent(context, bundle))
        }

        fun getIntent(context: Context, bundle: SuccessScreenStartBundle) =
                Intent(context, SuccessScreenActivity::class.java).apply {
                    putExtra(QUIZ_ID_EXTRA, bundle.quizId)
                    putExtra(ANSWERS_EXTRA, AnswersWrapper(bundle.answers))
                }
    }
}