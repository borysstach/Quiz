package pl.borys.quiz.usecase.quizDetails

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.quizzes_list_activity.*
import org.kodein.di.generic.instance
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.extra
import pl.borys.quiz.common.view.hideLoader
import pl.borys.quiz.common.view.showError
import pl.borys.quiz.common.view.showLoader
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.model.dto.QuizId
import pl.borys.quiz.usecase.quizDetails.dto.QuizPage

class QuizDetailsActivity : AppCompatActivity() {

    private val quizId: QuizId by extra(QUIZ_ID_EXTRA)
    private val quizDetailsVM: QuizDetailsViewModel by KodeinProvider.kodeinInstance.instance(arg = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_details_activity)
        observeQuizPages()
    }

    private fun observeQuizPages() {
        quizDetailsVM.observeQuizPages(quizId).observe(this, processResponse)
    }

    private val processResponse = Observer<QuizPageResponse> { response ->
        response?.map(
                onLoading = showLoader,
                onSuccess = showQuiz,
                onError = showError,
                onFinish = hideLoader
        )
    }

    private val showLoader: () -> Unit = {
        coordinator.showLoader()
    }

    private val hideLoader: () -> Unit = {
        coordinator.hideLoader()
    }

    private val showQuiz: (QuizPage?) -> Unit = { quizPage ->

    }

    private val showError: (Throwable?) -> Unit = {
        coordinator.showError { observeQuizPages() }
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