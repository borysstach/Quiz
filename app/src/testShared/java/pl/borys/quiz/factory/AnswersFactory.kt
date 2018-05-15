package pl.borys.quiz.factory

import pl.borys.quiz.model.dto.Answer

object AnswersFactory {
    val TEXT = "TestAnswerText"
    val IMAGE = PhotoFactory.getPhoto()

    fun getAnswersList(correctList: List<Boolean> = listOf(false)) =
            correctList.mapIndexed { index: Int, correct: Boolean ->
                getAnswer(
                        correct = correct,
                        order = index
                )
            }

    fun getAnswer(correct: Boolean = false, order: Int = 0) = Answer(
            text = TEXT,
            image = IMAGE,
            isCorrect = correct,
            order = order
    )
}