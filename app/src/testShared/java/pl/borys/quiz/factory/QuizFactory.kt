package pl.borys.quiz.factory

import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.model.dto.QuizDetails
import pl.borys.quiz.usecase.quizDetails.dto.QuizPage

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

    fun getQuizPage() = QuizPage(
            page = 0,
            pages = QUESTIONS,
            quizTitle = TITLE,
            question = QuestionFactory.getQuestion()
    )
}