package pl.borys.quiz.common

import pl.borys.quiz.BuildConfig

fun isRelease() = BuildConfig.BUILD_TYPE == "release"