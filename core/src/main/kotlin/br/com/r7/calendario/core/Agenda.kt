package br.com.r7.calendario.core

import java.time.LocalDateTime

data class Agenda(
        val id: Long?,
        val nome: String,
        val descricao: String,
        val idUsuario: Long,
        val dataCadastro: LocalDateTime = LocalDateTime.now()
)