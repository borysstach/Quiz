package pl.borys.quiz.helper.testRules

import org.kodein.di.Kodein
import pl.borys.quiz.usecase.quizzesList.view.QuizzesListActivity

class QuizzesListActivityTestRule(kodein: Kodein) : BaseActivityTestRule<QuizzesListActivity>(
        kodein = kodein,
        clazz = QuizzesListActivity::class.java
)