package pl.borys.quiz

import android.app.Application
import org.kodein.di.KodeinAware
import pl.borys.quiz.di.KodeinProvider

class QuizApplication: Application(), KodeinAware {
    override val kodein = KodeinProvider.kodeinInstance
}