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

typealias QuizDetailsResponse = Response<QuizDetails>

class QuizDetailsViewModel : ViewModel() {
    private val quizDetailsLiveData: MutableLiveData<QuizDetailsResponse> = MutableLiveData()
    private val quizzesRepository: QuizzesRepository by KodeinProvider.kodeinInstance.instance()
    private var quizDisposable: Disposable? = null

    fun getQuizDetails(quizId: QuizId): LiveData<QuizDetailsResponse> {
        val neverCalled = quizDetailsLiveData.value == null
        val wasError = quizDetailsLiveData.value?.isError() ?: true
        if (neverCalled || wasError) {
            getQuizDetailsFromRepo(quizId)
        }
        return quizDetailsLiveData
    }

    private fun getQuizDetailsFromRepo(quizId: QuizId){
        quizDisposable = quizzesRepository.getQuizDetails(quizId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    postLoading()
                }
                .subscribe(
                        postSuccess,
                        postError
                )
    }

    private val postSuccess: (QuizDetails) -> Unit = {
        quizDetailsLiveData.setValue(Response.success(it))
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