package br.com.r7.calendario.dto

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Evento
import java.time.LocalDateTime
import javax.validation.constraints.*

data class EventoDTO(
        val id: Long? = null,
        @field:NotBlank(message = "{validation.notBlank}") val titulo: String = "",
        @Future val dataInicio: LocalDateTime = LocalDateTime.now(),
        @Future val dataFim: LocalDateTime = LocalDateTime.now().plusHours(1),
        @Email.List(Email(message = "{validation.emailInvalid}")) @NotEmpty val convidados: List<String> = listOf(),
        @field:NotBlank(message = "{validation.notBlank}") @Size(max = 255, message = "{validation.size.255}") val descricao: String = "",
        @NotNull(message = "{validation.notNull}") val idAgenda: Long? = null,
        val idUsuario: Long? = null
)

fun Evento.toEventoDTO() = EventoDTO(
        id = id,
        titulo = titulo,
        descricao = descricao,
        dataInicio = dataInicio,
        dataFim = dataFim,
        convidados = convidados,
        idAgenda = idAgenda,
        idUsuario = idUsuario
)

fun EventoDTO.toEvento(idUsuario: Long) = Evento(
        id = id,
        titulo = titulo,
        descricao = descricao,
        dataInicio = dataInicio,
        dataFim = dataFim,
        convidados = convidados,
        idAgenda = idAgenda!!,
        idUsuario = idUsuario
)
