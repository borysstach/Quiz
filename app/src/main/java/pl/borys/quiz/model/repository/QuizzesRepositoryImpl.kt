package pl.borys.quiz.model.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pl.borys.quiz.common.retrofit.RetrofitProvider
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails

class QuizzesRepositoryImpl : QuizzesRepository {
    private val quizApi = RetrofitProvider.get().create(QuizApi::class.java)

    override fun getQuizzesList(page: Int): Observable<List<QuizCard>> {
        val startItem = page * ITEMS_PER_PAGE
        val listSize = ITEMS_PER_PAGE
        return quizApi.getQuizzesList(startItem, listSize).map { it.items }
    }

    override fun getQuizDetails(quizId: Long): Observable<QuizDetails> =
            quizApi.getQuizDetails(quizId)
                    .observeOn(Schedulers.computation())
                    .map {
                        it.copy(questions = it
                                .questions
                                .sortedBy { it.order }
                        )
                    }

    companion object {
        const val ITEMS_PER_PAGE = 50
    }
}