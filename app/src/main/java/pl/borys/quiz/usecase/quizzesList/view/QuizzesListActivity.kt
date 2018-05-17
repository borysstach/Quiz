package pl.borys.quiz.usecase.quizzesList.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.quizzes_list_activity.*
import org.kodein.di.generic.instance
import pl.borys.quiz.R
import pl.borys.quiz.common.view.RecyclerViewMargin
import pl.borys.quiz.common.view.hideLoader
import pl.borys.quiz.common.view.showError
import pl.borys.quiz.common.view.showLoader
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.usecase.quizDetails.view.QuizDetailsActivity
import pl.borys.quiz.usecase.quizzesList.QuizzesListResponse
import pl.borys.quiz.usecase.quizzesList.QuizzesListViewModel

class QuizzesListActivity : AppCompatActivity() {

    private val quizzesListVM: QuizzesListViewModel by KodeinProvider.kodeinInstance.instance(arg = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizzes_list_activity)
        getQuizzesList()
        observeViewModelActions()
    }

    private fun getQuizzesList() {
        quizzesListVM.getQuizzesList().observe(this, processResponse)
    }

    private val processResponse = Observer<QuizzesListResponse> { response ->
        response?.map(
                onLoading = showLoader,
                onSuccess = showQuizzes,
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

    private val showQuizzes: (List<QuizCard>?) -> Unit = { quizzesCards ->
        recycler.apply {
            val cardMarginInPixels = resources.getDimensionPixelSize(R.dimen.margin_tiny)
            addItemDecoration(RecyclerViewMargin(cardMarginInPixels))
            layoutManager = LinearLayoutManager(this@QuizzesListActivity)
            adapter = QuizzesListAdapter(quizzesCards)
        }
    }

    private val showError: (Throwable?) -> Unit = {
        coordinator.showError { getQuizzesList() }
    }

    private fun observeViewModelActions() {
        quizzesListVM.observeStartQuizDetailsAction(this, Observer { quizId ->
            quizId?.let{
                QuizDetailsActivity.start(this, quizId)
            }
        })
    }

}
