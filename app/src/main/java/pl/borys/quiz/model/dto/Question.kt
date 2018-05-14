package pl.borys.quiz.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
        val text: String,
        val image: Photo,
        val answers: List<Answer>,
        val order: Int
) : Parcelable