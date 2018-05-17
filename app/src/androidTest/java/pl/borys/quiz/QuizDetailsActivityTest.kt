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
import pl.borys.quiz.di.getQuizDetailsViewModelModule
import pl.borys.quiz.factory.QuestionFactory
import pl.borys.quiz.factory.QuizFactory
import pl.borys.quiz.helper.espresso.assertIsDisplayed
import pl.borys.quiz.helper.espresso.getTargetContext
import pl.borys.quiz.helper.espresso.hasText
import pl.borys.quiz.helper.testRules.QuizDetailsActivityTestRule
import pl.borys.quiz.usecase.quizDetails.QuizDetailsViewModel
import pl.borys.quiz.usecase.quizDetails.QuizPageResponse


@RunWith(AndroidJUnit4::class)
@LargeTest
class QuizDetailsActivityTest {
    private val mockViewModel = mock(QuizDetailsViewModel::class.java)
    private val quizLiveData: MutableLiveData<QuizPageResponse> = MutableLiveData()

    private val quizViewModelModule: Kodein.Module
        get() {
            Mockito.`when`(mockViewModel.observeQuizPages(1L)).thenReturn(quizLiveData)
            return getQuizDetailsViewModelModule(mockViewModel, overrides = true)
        }


    @get:Rule
    var activityRule = QuizDetailsActivityTestRule(getTargetContext(), TestKodein.getWith(quizViewModelModule))

    @Test
    fun should_ShowQuizTitle() {
        quizLiveData.postValue(
                Response.success(
                        QuizFactory.getQuizPage()
                )
        )
        R.id.quizTitle hasText QuizFactory.TITLE
    }

    @Test
    fun should_ShowErrorData() {
        val error = Throwable("Awesome test error")
        quizLiveData.postValue(
                Response.error(
                        error
                )
        )
        R.id.fullscreenError.assertIsDisplayed()
    }

    @Test
    fun should_ShowQuestion() {
        quizLiveData.postValue(
                Response.success(
                        QuizFactory.getQuizPage()
                )
        )
        R.id.question hasText QuestionFactory.TEXT
    }
}