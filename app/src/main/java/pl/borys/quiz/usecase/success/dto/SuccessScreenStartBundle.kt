package pl.borys.quiz.usecase.success.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.borys.quiz.model.dto.QuizId

@Parcelize
data class SuccessScreenStartBundle(
        val quizId: QuizId,
        val answers: List<Boolean>
) : Parcelable