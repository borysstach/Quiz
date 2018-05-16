package pl.borys.quiz.helper.testRules

import android.app.Activity
import android.support.test.rule.ActivityTestRule
import org.kodein.di.Kodein
import pl.borys.quiz.di.TestKodein
import pl.borys.quiz.di.KodeinProvider

abstract class BaseActivityTestRule<T : Activity>(
        private val kodein: Kodein = TestKodein.get(),
        clazz: Class<T>
) : ActivityTestRule<T>(clazz) {

    override fun beforeActivityLaunched() {
        KodeinProvider.override(kodein)
        super.beforeActivityLaunched()
    }
}