package pl.borys.quiz.usecase.success

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.success_activity.*
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.extra
import pl.borys.quiz.common.extensions.extraParcelable
import pl.borys.quiz.model.dto.QuizId
import pl.borys.quiz.usecase.quizDetails.view.QuizDetailsActivity
import pl.borys.quiz.usecase.quizzesList.view.QuizzesListActivity
import pl.borys.quiz.usecase.success.dto.AnswersWrapper
import pl.borys.quiz.usecase.success.dto.SuccessScreenStartBundle

class SuccessScreenActivity : AppCompatActivity() {

    private val quizId: QuizId by extra(QUIZ_ID_EXTRA)
    private val answers: AnswersWrapper by extraParcelable(ANSWERS_EXTRA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_activity)
        bind()
    }

    private fun bind() {
        bindScore()
        bindTryAgainButton()
        bindComebackHomeButton()
    }

    private fun bindScore(){
        val percentage = getSuccessPercentage().toInt()
        score.text = String.format(getString(R.string.score), percentage)
    }

    private fun bindTryAgainButton(){
        tryAgain.setOnClickListener {
            finish()
            QuizDetailsActivity.start(this, quizId)
        }
    }

    private fun bindComebackHomeButton(){
        comebackHome.setOnClickListener {
            QuizzesListActivity.start(this)
        }
    }

    private fun getSuccessPercentage(): Double {
        val correctAnswers: Double = answers.items.filter { it }.size.toDouble()
        val allAnswers: Double = answers.items.size.toDouble()
        return correctAnswers / allAnswers * 100
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