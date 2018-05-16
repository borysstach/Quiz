package pl.borys.quiz.usecase.quizzesList.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.borys.quiz.model.dto.QuizCard

class QuizzesListAdapter(
        val quizzesList: List<QuizCard>?
) : RecyclerView.Adapter<QuizCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = QuizCardViewHolder(parent)

    override fun getItemCount() = quizzesList?.size?: 0

    override fun onBindViewHolder(holder: QuizCardViewHolder, position: Int) {
        holder.bind(quizzesList?.get(position))
    }
}