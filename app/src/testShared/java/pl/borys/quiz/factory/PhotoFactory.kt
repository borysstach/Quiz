package pl.borys.quiz.factory

import pl.borys.quiz.model.dto.Photo

object PhotoFactory {
    fun getPhoto() = Photo(
            url = "testQuizUrl"
    )
}