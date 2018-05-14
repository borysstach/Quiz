package pl.borys.quiz.model.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.model.repository.QuizzesRepositoryImpl

val quizzesModule = Kodein.Module {
    bind<QuizzesRepository>() with singleton { QuizzesRepositoryImpl() }
}