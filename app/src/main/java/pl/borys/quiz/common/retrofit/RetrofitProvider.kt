package pl.borys.quiz.common.retrofit

import org.kodein.di.generic.instance
import pl.borys.quiz.di.KodeinProvider
import retrofit2.Retrofit

object RetrofitProvider {
    private val retrofit : Retrofit by KodeinProvider.kodeinInstance.instance()
    fun get(): Retrofit = retrofit
}