package pl.borys.quiz.usecase.success.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswersWrapper(
        val items: List<Boolean>
) : Parcelable