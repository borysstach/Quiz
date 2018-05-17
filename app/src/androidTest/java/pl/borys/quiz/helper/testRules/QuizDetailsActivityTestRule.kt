package pl.borys.quiz.helper.testRules

import android.content.Context
import org.kodein.di.Kodein
import pl.borys.quiz.usecase.quizDetails.QuizDetailsActivity

class QuizDetailsActivityTestRule(context: Context, kodein: Kodein) : BaseIntentActivityTestRule<QuizDetailsActivity>(
        intent = QuizDetailsActivity.getStartIntent(context, 1L),
        kodein = kodein,
        clazz = QuizDetailsActivity::class.java
)