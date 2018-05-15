package pl.borys.quiz

import pl.borys.quiz.model.dto.Photo

object PhotoFactory {
    fun getPhoto() = Photo(
            url = "testQuizUrl"
    )
}