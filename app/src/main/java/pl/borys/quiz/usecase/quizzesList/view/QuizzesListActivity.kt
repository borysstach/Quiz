package pl.borys.quiz.usecase.quizzesList.view

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.quizzes_list_activity.*
import org.kodein.di.generic.instance
import pl.borys.quiz.R
import pl.borys.quiz.model.di.KodeinProvider
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.usecase.quizzesList.QuizzesListResponse
import pl.borys.quiz.usecase.quizzesList.QuizzesListViewModel

class QuizzesListActivity : AppCompatActivity() {

    private val quizzesListVM: QuizzesListViewModel by KodeinProvider.kodeinInstance.instance(arg = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizzes_list_activity)
        getQuizzesList()
    }

    private fun getQuizzesList(){
        quizzesListVM.getQuizzesList().observe(this, processResponse)
    }

    private val processResponse = Observer<QuizzesListResponse> { response ->
        response?.map(
                onLoading = showLoader,
                onSuccess = changeMessage,
                onError = showError,
                onFinish = hideLoader
        )
    }

    private val showLoader: () -> Unit = {
    }

    private val hideLoader: () -> Unit = {
    }

    private val changeMessage: (List<QuizCard>?) -> Unit = { quizzesCards ->
        message.text = quizzesCards?.map { it.title }.toString()
    }

    private val showError: (Throwable?) -> Unit = {
        message.text = it?.message ?: "Error with no message"
    }
}
