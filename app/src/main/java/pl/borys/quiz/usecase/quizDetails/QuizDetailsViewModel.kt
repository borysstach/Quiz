package pl.borys.quiz.usecase.quizDetails

import android.arch.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.kodein.di.generic.instance
import pl.borys.quiz.common.viewModel.ActionLiveData
import pl.borys.quiz.common.viewModel.Response
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.model.dto.QuizId
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.usecase.quizDetails.dto.QuizPage
import pl.borys.quiz.usecase.quizDetails.events.AnswerClickedEvent
import pl.borys.quiz.usecase.success.dto.SuccessScreenStartBundle

typealias QuizPageResponse = Response<QuizPage>

class QuizDetailsViewModel : ViewModel() {
    private val quizDetailsLiveData: MutableLiveData<QuizPageResponse> = MutableLiveData()
    private val openSuccessScreenAction = ActionLiveData<SuccessScreenStartBundle>()
    private val quizzesRepository: QuizzesRepository by KodeinProvider.kodeinInstance.instance()
    private var quizDisposable: Disposable? = null
    private var quizDetails: QuizDetails? = null
    private var answers = mutableListOf<Boolean>()

    init {
        EventBus.getDefault().register(this)
    }

    fun observeOpenSuccessScreenAction(lifecycleOwner: LifecycleOwner, observer: Observer<SuccessScreenStartBundle?>) {
        openSuccessScreenAction.observe(lifecycleOwner, observer)
    }

    @Subscribe
    fun onAnswerClicked(event: AnswerClickedEvent) {
        postLoading()
        answers.add(event.answer.isCorrect)
        if (answers.size < quizDetails?.questions?.size ?: 0) {
            postNextQuestion()
        } else {
            openSuccessScreenAction.sendAction(
                    SuccessScreenStartBundle(
                            quizId = quizDetails!!.id,
                            answers = answers
                    )
            )
        }
    }

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
        if (quizDetails != null) {
            val pageIndex = answers.size
            val nextQuestion = quizDetails!!.questions[pageIndex]
            val nextPage = QuizPage(
                    page = pageIndex,
                    pages = quizDetails!!.questions.size,
                    quizTitle = quizDetails!!.title,
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
        EventBus.getDefault().unregister(this)
        super.onCleared()
    }
}