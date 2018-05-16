package pl.borys.quiz.di

import org.kodein.di.Kodein

object TestKodein {

    fun get() = Kodein {
        import(getQuizzesRepositoryModule(overrides = false), allowOverride = true)
        import(getQuizzesListViewModelModule(overrides = false), allowOverride = true)
        import(getRetrofitModule(), allowOverride = false)
    }

    fun getWith(vararg module: Kodein.Module) = Kodein {
        extend(get(), allowOverride = true)
        module.forEach {
            import(module = it, allowOverride = true)
        }
    }

}