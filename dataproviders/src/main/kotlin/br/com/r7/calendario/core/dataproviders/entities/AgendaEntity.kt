package br.com.r7.calendario.core.dataproviders.entities

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Usuario
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "agendas")
data class AgendaEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
        @Column val nome: String,
        @Column(length = 255) val descricao: String,
        @JoinColumn(name = "id_usuario",referencedColumnName = "id") @ManyToOne val usuario: UsuarioEntity,
        @Column(name = "data_cadastro") val dataCadastro: LocalDateTime
)

fun AgendaEntity.toUsuario() = Agenda(
        id = id,
        nome = nome,
        descricao = descricao,
        idUsuario = usuario.id!!,
        dataCadastro = dataCadastro
)

fun Agenda.toEntity() = AgendaEntity(
        id = id,
        nome = nome,
        descricao = descricao,
        usuario = UsuarioEntity(idUsuario),
        dataCadastro = dataCadastro
)