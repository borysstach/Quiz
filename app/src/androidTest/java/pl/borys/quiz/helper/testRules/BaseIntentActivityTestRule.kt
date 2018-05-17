package pl.borys.quiz.helper.testRules

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import org.kodein.di.Kodein
import pl.borys.quiz.di.KodeinProvider
import pl.borys.quiz.di.TestKodein

abstract class BaseIntentActivityTestRule<T : Activity>(
        private val intent: Intent,
        private val kodein: Kodein = TestKodein.get(),
        clazz: Class<T>
) : ActivityTestRule<T>(clazz) {

    override fun getActivityIntent(): Intent {
        return intent
    }

    override fun beforeActivityLaunched() {
        KodeinProvider.override(kodein)
        super.beforeActivityLaunched()
    }
}