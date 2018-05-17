package pl.borys.quiz.usecase.quizzesList

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
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizId
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.usecase.quizzesList.events.QuizCardClickedEvent

typealias QuizzesListResponse = Response<List<QuizCard>>

class QuizzesListViewModel : ViewModel() {

    private val quizzesListsLiveData: MutableLiveData<QuizzesListResponse> = MutableLiveData()
    private val startQuizDetailsActivityAction = ActionLiveData<QuizId>()
    private val quizzesRepository: QuizzesRepository by KodeinProvider.kodeinInstance.instance()

    private var quizzesDisposable: Disposable? = null

    init {
        EventBus.getDefault().register(this)
    }

    fun observeStartQuizDetailsAction(lifecycleOwner: LifecycleOwner, observer: Observer<QuizId?>){
        startQuizDetailsActivityAction.observe(lifecycleOwner, observer)
    }

    fun getQuizzesList(): LiveData<QuizzesListResponse> {
        val neverCalled = quizzesListsLiveData.value == null
        val wasError = quizzesListsLiveData.value?.isError() ?: true
        if (neverCalled || wasError) {
            getQuizzesFromRepo()
        }
        return quizzesListsLiveData
    }

    private fun getQuizzesFromRepo() {
        quizzesDisposable = quizzesRepository.getQuizzesList(0)
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

    private val postSuccess: (List<QuizCard>) -> Unit = {
        quizzesListsLiveData.setValue(Response.success(it))
    }

    private val postError: (Throwable) -> Unit = {
        quizzesListsLiveData.setValue(Response.error(it))
    }

    private val postLoading: () -> Unit = {
        quizzesListsLiveData.setValue(Response.loading())
    }

    @Subscribe
    fun onQuizCardCLicked(event: QuizCardClickedEvent) {
        startQuizDetailsActivityAction.sendAction(event.id)
    }

    override fun onCleared() {
        EventBus.getDefault().unregister(this)
        quizzesDisposable?.dispose()
        super.onCleared()
    }
}