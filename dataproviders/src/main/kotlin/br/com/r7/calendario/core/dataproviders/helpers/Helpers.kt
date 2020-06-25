package br.com.r7.calendario.core.dataproviders.helpers

import java.util.*

fun <T> Optional<T>.unwrap(): T? = orElse(null)

fun <T> T.unwrap(): T? = this

fun <T, E> Optional<T>.unwrap(conv: (T) -> (E)): E? = unwrap()?.let { conv(it) }

fun <T, E> T.unwrap(conv: (T) -> (E)): E =  conv(this)