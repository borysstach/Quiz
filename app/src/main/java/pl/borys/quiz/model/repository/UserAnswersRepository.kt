package pl.borys.quiz.model.repository

import io.reactivex.Observable

typealias UserAnswers = List<Boolean?>

interface UserAnswersRepository {
    fun getAnswersList(page: Int): Observable<List<UserAnswers>>
    fun getAnswers(quizId: Long): Observable<UserAnswers>
    fun putAnswers(quizId: Long, answers: UserAnswers): Observable<Boolean>
}


