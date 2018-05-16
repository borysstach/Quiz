package pl.borys.quiz.usecase.quizzesList.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import pl.borys.quiz.R
import pl.borys.quiz.model.dto.QuizCard

class QuizCardViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.quiz_card, parent, false)) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val userResult: TextView = itemView.findViewById(R.id.userResult)
    private val backgroundPhoto: ImageView = itemView.findViewById(R.id.backgroundPhoto)

    fun bind(quizCard: QuizCard?) {
        title.text = quizCard?.title
        //TODO: bind photo with glide
        //TODO: bind user result
    }
}