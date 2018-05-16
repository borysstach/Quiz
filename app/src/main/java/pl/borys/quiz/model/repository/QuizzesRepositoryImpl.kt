package pl.borys.quiz.model.repository

import io.reactivex.Observable
import pl.borys.quiz.model.dto.Photo
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails
import java.lang.Exception

class QuizzesRepositoryImpl : QuizzesRepository {
    override fun getQuizzesList(page: Int): Observable<List<QuizCard>> =
            Observable.just(listOf(
                    QuizCard(
                            id = 1L,
                            title = "QuizTitle",
                            mainPhoto = Photo("https://photos.app.goo.gl/Eewlv9RIyDecyq642"),
                            questions = 5
                    )
            ))

    override fun getQuizDetails(quizId: Long): Observable<QuizDetails> =
            Observable.error(Exception("Not implemented yet :("))
}