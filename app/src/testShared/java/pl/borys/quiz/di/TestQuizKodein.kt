package pl.borys.quiz.di

import android.arch.lifecycle.MutableLiveData
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.singleton
import org.mockito.Mockito
import pl.borys.quiz.common.viewModel.Response
import pl.borys.quiz.factory.QuizFactory
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.usecase.quizzesList.QuizzesListResponse
import pl.borys.quiz.usecase.quizzesList.QuizzesListViewModel

fun getQuizzesRepositoryModule(
        quizzesList: List<QuizCard> = listOf(QuizFactory.getQuizCard()),
        quizDetails: QuizDetails = QuizFactory.getQuizDetails(),
        overrides: Boolean = true
) = Kodein.Module {
    bind<QuizzesRepository>(overrides = overrides) with singleton {
        object : QuizzesRepository {
            override fun getQuizzesList(page: Int): Observable<List<QuizCard>> =
                    Observable.just(quizzesList)

            override fun getQuizDetails(quizId: Long): Observable<QuizDetails> =
                    Observable.just(quizDetails)
        }
    }
}

fun getQuizzesListViewModelModule(
        quizzesVM: QuizzesListViewModel? = null,
        overrides: Boolean = true
) = Kodein.Module {
    bind<QuizzesListViewModel>(overrides = overrides) with factory { activity: AppCompatActivity ->
        quizzesVM ?: mockNewViewModel()
    }
}

private fun mockNewViewModel(): QuizzesListViewModel {
    val newVoteVM = Mockito.mock(QuizzesListViewModel::class.java)
    val liveData: MutableLiveData<QuizzesListResponse> = MutableLiveData()
    liveData.postValue(
            Response.success(
                    listOf(
                            QuizFactory.getQuizCard()
                    )
            ))
    Mockito.`when`(newVoteVM.getQuizzesList()).thenReturn(liveData)
    return newVoteVM
}