package pl.borys.quiz.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizCardListWrapper(
        val items: List<QuizCard>
) : Parcelable