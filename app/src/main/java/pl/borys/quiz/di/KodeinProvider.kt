package pl.borys.quiz.di

import android.support.annotation.VisibleForTesting
import org.kodein.di.Kodein

object KodeinProvider {
    var kodeinInstance = Kodein {
        import(quizzesModule)
        import(userAnswersModule)
        import(getRetrofitModule)
    }
        private set

    @VisibleForTesting()
    fun override(kodein: Kodein) {
        kodeinInstance = kodein
    }
}