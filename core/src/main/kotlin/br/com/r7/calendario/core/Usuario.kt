package br.com.r7.calendario.core

import java.time.LocalDateTime

data class Usuario(
        val id: Long?,
        val nome: String,
        val login: String,
        val senha: String,
        val dataCadastro: LocalDateTime
)
