package pl.borys.quiz.usecase.quizDetails.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.quiz_details_activity.*
import org.kodein.di.generic.instance
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.extra
import pl.borys.quiz.common.extensions.loadWithPlaceholder
import pl.borys.quiz.common.view.hideLoader
import pl.borys.quiz.common.view.showError
import pl.borys.quiz.common.view.showLoader
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.model.dto.QuizId
import pl.borys.quiz.usecase.quizDetails.QuizDetailsViewModel
import pl.borys.quiz.usecase.quizDetails.QuizPageResponse
import pl.borys.quiz.usecase.quizDetails.dto.QuizPage

class QuizDetailsActivity : AppCompatActivity() {

    private val quizId: QuizId by extra(QUIZ_ID_EXTRA)
    private val quizDetailsVM: QuizDetailsViewModel by KodeinProvider.kodeinInstance.instance(arg = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_details_activity)
        observeQuizPages()
        observeViewModelActions()
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
        quizPage?.let {
            progressBar.apply {
                max = it.pages
                progress = it.page
            }
            quizTitle.text = it.quizTitle
            questionImage.loadWithPlaceholder(it.question.image)
            question.text = it.question.text
            answers.apply {
                bind(it.question.answers)
            }
        }
    }

    private val showError: (Throwable?) -> Unit = {
        coordinator.showError { observeQuizPages() }
    }

    private fun observeViewModelActions(){
        quizDetailsVM.observeOpenSuccessScreenAction(this, Observer {
            //TODO: open success screen
        })
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