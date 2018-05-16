package pl.borys.quiz

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.mockito.Mockito.*
import pl.borys.quiz.common.extensions.emitAfter
import pl.borys.quiz.common.viewModel.Response
import pl.borys.quiz.di.TestKodein
import pl.borys.quiz.extensions.mock
import pl.borys.quiz.factory.QuizFactory
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.usecase.quizzesList.QuizzesListResponse
import pl.borys.quiz.usecase.quizzesList.QuizzesListViewModel
import java.util.concurrent.TimeUnit
import org.junit.Assert.*


@RunWith(JUnit4::class)
class VoteViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun doOnStart() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    private val quizzesListVM by lazy { QuizzesListViewModel() }
    private val observer = mock<Observer<QuizzesListResponse>>()
    private var getQuizzesListCalled = 0


    @Test
    fun getQuizzesList_CallForRepository_OnlyOnce_whenSuccess(){
        initWithQuizzesList()
        getQuizzesListCalled = 0
        quizzesListVM.getQuizzesList().observeForever(observer)
        quizzesListVM.getQuizzesList().observeForever(observer)
        assertEquals(1, getQuizzesListCalled)
    }

    @Test
    fun getQuizzesList_CallForRepository_Again_WhenError(){
        initWithQuizzesList(delay = 0, error = Throwable())
        getQuizzesListCalled = 0
        quizzesListVM.getQuizzesList().observeForever(observer)
        quizzesListVM.getQuizzesList().observeForever(observer)
        assertEquals(2, getQuizzesListCalled)
    }

    @Test
    fun getQuizzesList_ReturnLoadResponse_WhileDataIsFetched() {
        initWithQuizzesList()
        quizzesListVM.getQuizzesList().observeForever(observer)

        verify(observer).onChanged(Response.loading())
    }

    @Test
    fun getQuizzesList_ReturnData_AfterFetchedDelay() {
        val delay = 3L
        val data = listOf(QuizFactory.getQuizCard())

        initWithQuizzesList(quizzesList = data, delay = delay)
        quizzesListVM.getQuizzesList().observeForever(observer)
        Thread.sleep((delay + 1) * 1000)

        verify(observer).onChanged(Response.success(data))
    }

    @Test
    fun getQuizzesList_ReturnError() {
        val error = Throwable("some dangerous error")

        initWithQuizzesList(delay = 0, error = error)
        quizzesListVM.getQuizzesList().observeForever(observer)
        Thread.sleep(1000)

        verify(observer).onChanged(Response.error(error))
    }

    private fun initWithQuizzesList(
            quizzesList: List<QuizCard> = listOf(QuizFactory.getQuizCard()),
            delay: Long = 3,
            error: Throwable? = null
    ) {
        val observable =
                if (error == null) {
                    Observable.just(quizzesList).emitAfter(delay, TimeUnit.SECONDS)
                } else {
                    Observable.error(error)
                }
        KodeinProvider.override(TestKodein.getWith(
                getQuizzesRepositoryWith(observable)
        ))
    }

    private fun getQuizzesRepositoryWith(observable: Observable<List<QuizCard>>): Kodein.Module =
            Kodein.Module {
                bind<QuizzesRepository>(overrides = true) with singleton {
                    object : QuizzesRepository {
                        override fun getQuizzesList(page: Int): Observable<List<QuizCard>> {
                            getQuizzesListCalled++
                            return observable
                        }

                        override fun getQuizDetails(quizId: Long): Observable<QuizDetails> =
                                Observable.error(Exception("Shouldn't be called in this tests"))
                    }
                }
            }
}