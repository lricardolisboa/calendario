package br.com.r7.calendario.core

import java.time.LocalDateTime

data class Evento(
        val id: Long?,
        val titulo: String,
        val dataInicio: LocalDateTime,
        val dataFim: LocalDateTime,
        val convidados: List<String>,
        val descricao: String,
        val idAgenda: Long,
        val idUsuario: Long
)