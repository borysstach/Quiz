package pl.borys.quiz.factory

import pl.borys.quiz.model.dto.Question

object QuestionFactory {
    val TEXT = "TestQuestionText"
    val IMAGE = PhotoFactory.getPhoto()
    val ANSWERS = AnswersFactory.getAnswersList()

    fun getQuestionsList(size: Int = 1) =
            (0 until size).map { order ->
                getQuestion(order)
            }

    fun getQuestion(order: Int = 0) =
            Question(
                    text = TEXT,
                    image = IMAGE,
                    answers = ANSWERS,
                    order = order
            )
}