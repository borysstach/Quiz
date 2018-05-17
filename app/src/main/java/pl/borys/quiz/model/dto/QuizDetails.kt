package pl.borys.quiz.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizDetails(
        val id: Long,
        val title: String,
        val mainPhoto: Photo,
        val questions: List<Question>
) : Parcelable