package pl.borys.quiz.model

import io.reactivex.Observable
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails

interface QuizzesRepository {
    fun getQuizzesList(page: Int): Observable<List<QuizCard>>
    fun getQuizDetails(quizId: Long): Observable<QuizDetails>
}