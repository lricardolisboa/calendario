package br.com.r7.calendario.core.dataproviders.entities

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Evento
import br.com.r7.calendario.core.Usuario
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "eventos")
data class EventoEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
        @Column val titulo: String,
        @Column(length = 255) val descricao: String,
        @JoinColumn(name = "id_usuario",referencedColumnName = "id") @ManyToOne val usuario: UsuarioEntity,
        @JoinColumn(name = "id_agenda",referencedColumnName = "id") @ManyToOne val agenda: AgendaEntity,
        @Column(name = "convidados") val convidados: String,
        @Column(name = "data_inicio") val dataInicio: LocalDateTime,
        @Column(name = "data_fim") val dataFim: LocalDateTime
)

fun EventoEntity.toEvento() = Evento(
        id = id,
        titulo = titulo,
        descricao = descricao,
        idUsuario = usuario.id!!,
        dataInicio = dataInicio,
        dataFim = dataFim,
        idAgenda = agenda.id!!,
        convidados = convidados.split(";")
)

fun Evento.toEntity() = EventoEntity(
        id = id,
        titulo = titulo,
        descricao = descricao,
        usuario = UsuarioEntity(id = idUsuario),
        dataInicio = dataInicio,
        dataFim = dataFim,
        agenda = AgendaEntity(id = idAgenda),
        convidados = convidados.joinToString(";")
)