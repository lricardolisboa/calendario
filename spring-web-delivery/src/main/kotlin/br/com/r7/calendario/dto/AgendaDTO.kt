package br.com.r7.calendario.dto

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Usuario
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.validation.constraints.*

@JsonIgnoreProperties()
data class AgendaDTO(val id: Long? = null,
                     @field:NotBlank(message = "{validation.notBlank}") val nome: String = "",
                     @field:NotBlank(message = "{validation.notBlank}") @field:Size(max = 255,message = "{validation.size.255}") val descricao: String = "",
                     @field:NotNull(message = "{validation.notNull}") var idUsuario: Long? = null)

fun Agenda.toAgendaDTO() = AgendaDTO(
        id = id,
        nome = nome,
        descricao = descricao,
        idUsuario = idUsuario
)

fun AgendaDTO.toAgenda() = Agenda(
        id = id,
        nome = nome,
        descricao = descricao,
        idUsuario = idUsuario!!
)
