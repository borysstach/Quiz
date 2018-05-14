package pl.borys.quiz.model.repository

import io.reactivex.Observable
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails
import java.lang.Exception

class QuizzesRepositoryImpl : QuizzesRepository {
    override fun getQuizzesList(page: Int): Observable<List<QuizCard>> =
            Observable.error(Exception("Not implemented yet :("))

    override fun getQuizDetails(quizId: Long): Observable<QuizDetails> =
            Observable.error(Exception("Not implemented yet :("))
}