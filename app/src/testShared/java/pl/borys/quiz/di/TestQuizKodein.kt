package pl.borys.quiz.di

import io.reactivex.Observable
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import pl.borys.quiz.factory.QuizFactory
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.model.repository.QuizzesRepository

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