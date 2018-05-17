package pl.borys.quiz

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.mockito.Mockito.verify
import pl.borys.quiz.common.extensions.emitAfter
import pl.borys.quiz.common.viewModel.Response
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.di.TestKodein
import pl.borys.quiz.extensions.mock
import pl.borys.quiz.factory.QuizFactory
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.usecase.quizDetails.QuizDetailsViewModel
import pl.borys.quiz.usecase.quizDetails.QuizPageResponse
import pl.borys.quiz.usecase.quizDetails.dto.QuizPage
import java.util.concurrent.TimeUnit


@RunWith(JUnit4::class)
class QuizDetailsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun doOnStart() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    private val quizDetailsVM by lazy { QuizDetailsViewModel() }
    private val observer = mock<Observer<QuizPageResponse>>()
    private var getQuizDetailsCalled = 0
    private val QUIZ_ID = 1L


    @Test
    fun getQuizDetails_CallForRepository_OnlyOnce_whenSuccess(){
        initWithQuizzesList(delay = 0)
        getQuizDetailsCalled = 0
        quizDetailsVM.observeQuizPages(QUIZ_ID).observeForever(observer)
        quizDetailsVM.observeQuizPages(QUIZ_ID).observeForever(observer)
        assertEquals(1, getQuizDetailsCalled)
    }

    @Test
    fun getQuizDetails_CallForRepository_Again_WhenError(){
        initWithQuizzesList(delay = 0, error = Throwable("some dangerous error"))
        getQuizDetailsCalled = 0
        quizDetailsVM.observeQuizPages(QUIZ_ID).observeForever(observer)
        quizDetailsVM.observeQuizPages(QUIZ_ID).observeForever(observer)
        assertEquals(2, getQuizDetailsCalled)
    }

    @Test
    fun getQuizDetails_ReturnLoadResponse_WhileDataIsFetched() {
        initWithQuizzesList()
        quizDetailsVM.observeQuizPages(QUIZ_ID).observeForever(observer)

        verify(observer).onChanged(Response.loading())
    }

    @Test
    fun getQuizDetails_ReturnFirstQuestion() {
        val data = QuizFactory.getQuizDetails()
        initWithQuizzesList(quizDetails = data, delay = 0)
        quizDetailsVM.observeQuizPages(QUIZ_ID).observeForever(observer)
        Thread.sleep(1000)

        val expectedPage = QuizPage(page = 0, pages = data.questions.size, question = data.questions[0])
        verify(observer).onChanged(Response.success(expectedPage))
    }

    @Test
    fun getQuizDetails_ReturnError() {
        val error = Throwable("some dangerous error")

        initWithQuizzesList(delay = 0, error = error)
        quizDetailsVM.observeQuizPages(QUIZ_ID).observeForever(observer)
        Thread.sleep(1000)

        verify(observer).onChanged(Response.error(error))
    }

    private fun initWithQuizzesList(
            quizDetails: QuizDetails = QuizFactory.getQuizDetails(),
            delay: Long = 3,
            error: Throwable? = null
    ) {
        val observable =
                if (error == null) {
                    Observable.just(quizDetails).emitAfter(delay, TimeUnit.SECONDS)
                } else {
                    Observable.error(error)
                }
        KodeinProvider.override(TestKodein.getWith(
                getQuizzesRepositoryWith(observable)
        ))
    }

    private fun getQuizzesRepositoryWith(observable: Observable<QuizDetails>): Kodein.Module =
            Kodein.Module {
                bind<QuizzesRepository>(overrides = true) with singleton {
                    object : QuizzesRepository {
                        override fun getQuizzesList(page: Int): Observable<List<QuizCard>> =
                            Observable.error(Exception("Shouldn't be called in this tests"))

                        override fun getQuizDetails(quizId: Long): Observable<QuizDetails> {
                            getQuizDetailsCalled++
                            return observable
                        }
                    }
                }
            }
}