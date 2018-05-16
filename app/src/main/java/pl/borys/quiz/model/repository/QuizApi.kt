package pl.borys.quiz.model.repository

import io.reactivex.Observable
import pl.borys.quiz.model.dto.QuizCardListWrapper
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.model.dto.QuizId
import retrofit2.http.GET
import retrofit2.http.Path

interface QuizApi {

    @GET("quizzes/{start_item}/{list_size}")
    fun getQuizzesList(
            @Path("start_item") startItem: Int,
            @Path("list_size") listSize: Int
    ): Observable<QuizCardListWrapper>

    @GET("quiz/{quiz_id}/0")
    fun getQuizDetails(
            @Path("quiz_id") quizId: QuizId
    ): Observable<List<QuizDetails>>
}