package pl.borys.quiz.di

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.singleton
import pl.borys.quiz.model.repository.QuizzesRepository
import pl.borys.quiz.model.repository.QuizzesRepositoryImpl
import pl.borys.quiz.usecase.quizDetails.QuizDetailsViewModel
import pl.borys.quiz.usecase.quizzesList.QuizzesListViewModel

val quizzesModule = Kodein.Module {
    bind<QuizzesRepository>() with singleton { QuizzesRepositoryImpl() }
    bind<QuizzesListViewModel>() with factory { activity: AppCompatActivity -> ViewModelProviders.of(activity).get(QuizzesListViewModel::class.java) }
    bind<QuizDetailsViewModel>() with factory { activity: AppCompatActivity -> ViewModelProviders.of(activity).get(QuizDetailsViewModel::class.java) }
}