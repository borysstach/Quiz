package pl.borys.quiz.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import pl.borys.quiz.common.isRelease
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val getRetrofitModule = Kodein.Module {
    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .baseUrl("https://quiz.o2.pl/api/v1/")
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}

private fun getOkHttpClient() =
        OkHttpClient().newBuilder()
                .addInterceptor(getLoggingInterceptor())
                .build()

private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                    if (isRelease()) {
                        HttpLoggingInterceptor.Level.NONE
                    } else {
                        HttpLoggingInterceptor.Level.BODY
                    }
        }