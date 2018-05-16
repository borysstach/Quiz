package pl.borys.quiz.common.errors

import android.util.Log
object ErrorLogger {
    fun logErrorToConsoleAndCrashlytic(error: Throwable?) {
        Log.e("Quiz", "exception: ", error)
        //TODO: implement crashlytisc
    }
}
