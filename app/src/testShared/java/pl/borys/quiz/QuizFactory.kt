package pl.borys.quiz

import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails

object QuizFactory {
    val ID = 1L
    val TITLE = "TestQuizTitle"
    val MAIN_PHOTO = PhotoFactory.getPhoto()
    val QUESTIONS = 4

    fun getQuizCard() = QuizCard(
            id = ID,
            title = TITLE,
            mainPhoto = MAIN_PHOTO,
            questions = QUESTIONS
    )

    fun getQuizDetails() = QuizDetails(
            id = ID,
            title = TITLE,
            mainPhoto = MAIN_PHOTO,
            questions = QuestionFactory.getQuestionsList(QUESTIONS)
    )
}