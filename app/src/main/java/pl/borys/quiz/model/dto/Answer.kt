package pl.borys.quiz.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(
        val text: String,
        val image: Photo,
        val isCorrect: Boolean,
        val order: Int
) : Parcelable