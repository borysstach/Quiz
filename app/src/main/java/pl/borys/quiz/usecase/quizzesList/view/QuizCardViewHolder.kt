package pl.borys.quiz.usecase.quizzesList.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.greenrobot.eventbus.EventBus
import pl.borys.quiz.R
import pl.borys.quiz.common.extensions.loadWithGlide
import pl.borys.quiz.model.dto.QuizCard
import pl.borys.quiz.usecase.quizzesList.events.QuizCardClickedEvent

class QuizCardViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.quiz_card, parent, false)) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val userResult: TextView = itemView.findViewById(R.id.userResult)
    private val backgroundPhoto: ImageView = itemView.findViewById(R.id.backgroundPhoto)

    fun bind(quizCard: QuizCard?) {
        quizCard?.let{
            title.text = it.title
            //TODO: bind user result
            backgroundPhoto.loadWithGlide(it.mainPhoto)

            itemView.setOnClickListener { _: View ->
                EventBus.getDefault().post(QuizCardClickedEvent(quizCard.id))
            }
        }
    }
}