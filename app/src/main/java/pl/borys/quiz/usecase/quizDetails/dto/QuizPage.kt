package pl.borys.quiz.usecase.quizDetails.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.borys.quiz.model.dto.Question

@Parcelize
data class QuizPage(
        val page: Int,
        val pages: Int,
        val quizTitle: String,
        val question: Question
): Parcelable