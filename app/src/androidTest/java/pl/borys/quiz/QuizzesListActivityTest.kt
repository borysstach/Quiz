package pl.borys.quiz

import android.arch.lifecycle.MutableLiveData
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.mockito.Mockito
import org.mockito.Mockito.mock
import pl.borys.quiz.common.viewModel.Response
import pl.borys.quiz.di.TestKodein
import pl.borys.quiz.di.getQuizzesListViewModelModule
import pl.borys.quiz.factory.QuizFactory
import pl.borys.quiz.helper.espresso.assertIsDisplayed
import pl.borys.quiz.helper.espresso.hasText
import pl.borys.quiz.helper.testRules.QuizzesListActivityTestRule
import pl.borys.quiz.usecase.quizzesList.QuizzesListResponse
import pl.borys.quiz.usecase.quizzesList.QuizzesListViewModel


@RunWith(AndroidJUnit4::class)
@LargeTest
class QuizzesListActivityTest {
    private val mockViewModel = mock(QuizzesListViewModel::class.java)
    private val quizzesLiveData: MutableLiveData<QuizzesListResponse> = MutableLiveData()

    private val quizzesViewModelModule: Kodein.Module
        get() {
            Mockito.`when`(mockViewModel.getQuizzesList()).thenReturn(quizzesLiveData)
            return getQuizzesListViewModelModule(mockViewModel, overrides = true)
        }


    @get:Rule
    var activityRule = QuizzesListActivityTestRule(TestKodein.getWith(quizzesViewModelModule))

    @Test
    fun should_ShowTitle() {
        val data = listOf(QuizFactory.getQuizCard())
        quizzesLiveData.postValue(
                Response.success(
                        data
                )
        )
        R.id.title hasText QuizFactory.TITLE
    }

    @Test
    fun should_ShowErrorData() {
        val error = Throwable("Awesome test error")
        quizzesLiveData.postValue(
                Response.error(
                        error
                )
        )
        R.id.fullscreenError.assertIsDisplayed()
    }
}