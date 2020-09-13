package br.com.r7.calendario.core.dataproviders.repository.agenda

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.core.dataproviders.entities.*
import br.com.r7.calendario.core.dataproviders.helpers.unwrap
import br.com.r7.calendario.usecases.gateway.AgendaRepository
import br.com.r7.calendario.usecases.gateway.UsuarioRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class AgendaRepositoryImpl(private val dbAgendaRepository: DBAgendaRepository) : AgendaRepository {

    override fun salvar(agenda: Agenda): Agenda {
        val usuarioEntity = agenda.toEntity()
        return this.dbAgendaRepository.save(usuarioEntity).unwrap(AgendaEntity::toAgenda)
    }

    override fun inserirAgendaPadrao(usuario: Usuario) {
        val agendaPadrao = Agenda(
                id = null,
                nome = usuario.nome,
                descricao = "Agenda ${usuario.nome}",
                idUsuario = usuario.id!!,
                dataCadastro = LocalDateTime.now()
        )

        this.salvar(agendaPadrao)
    }

}