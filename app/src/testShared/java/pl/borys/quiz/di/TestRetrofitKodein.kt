package pl.borys.quiz.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import retrofit2.Retrofit


fun getRetrofitModule() = Kodein.Module {
    bind<Retrofit>() with singleton {
        getTestRetrofit()
    }
}

private fun getTestRetrofit() : Retrofit {
    throw RuntimeException("Don't use internet connection in tests!")
}