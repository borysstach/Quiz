package pl.borys.quiz.common.extensions

import android.net.Uri

fun String.toUri(): Uri = Uri.parse(this)