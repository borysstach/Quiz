package pl.borys.quiz.usecase.quizDetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance
import pl.borys.quiz.common.viewModel.Response
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.model.dto.QuizId
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.usecase.quizDetails.dto.QuizPage

typealias QuizPageResponse = Response<QuizPage>

class QuizDetailsViewModel : ViewModel() {
    private val quizDetailsLiveData: MutableLiveData<QuizPageResponse> = MutableLiveData()
    private val quizzesRepository: QuizzesRepository by KodeinProvider.kodeinInstance.instance()
    private var quizDisposable: Disposable? = null
    private var quizDetails: QuizDetails? = null
    private var answers: List<Boolean> = listOf()

    fun observeQuizPages(quizId: QuizId): LiveData<QuizPageResponse> {
        val neverCalled = quizDetails == null
        val wasError = quizDetailsLiveData.value?.isError() ?: false
        if (neverCalled || wasError) {
            getQuizDetailsFromRepo(quizId)
        }
        return quizDetailsLiveData
    }

    private fun getQuizDetailsFromRepo(quizId: QuizId) {
        quizDisposable = quizzesRepository.getQuizDetails(quizId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    postLoading()
                }
                .subscribe(
                        saveDetails,
                        postError
                )
    }


    private val saveDetails: (QuizDetails) -> Unit = {
        quizDetails = it
        postNextQuestion()
    }

    private fun postNextQuestion() {
        if(quizDetails != null) {
            val pageIndex = answers.size
            val nextQuestion = quizDetails!!.questions[pageIndex]
            val nextPage = QuizPage(
                    page = pageIndex,
                    pages = quizDetails!!.questions.size,
                    question = nextQuestion
            )
            quizDetailsLiveData.setValue(Response.success(nextPage))
        }
    }

    private val postError: (Throwable) -> Unit = {
        quizDetailsLiveData.setValue(Response.error(it))
    }

    private val postLoading: () -> Unit = {
        quizDetailsLiveData.setValue(Response.loading())
    }

    override fun onCleared() {
        quizDisposable?.dispose()
        super.onCleared()
    }
}