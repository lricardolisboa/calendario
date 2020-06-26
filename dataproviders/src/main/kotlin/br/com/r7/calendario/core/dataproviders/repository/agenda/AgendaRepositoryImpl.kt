package br.com.r7.calendario.core.dataproviders.repository.agenda

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.core.dataproviders.entities.AgendaEntity
import br.com.r7.calendario.core.dataproviders.entities.UsuarioEntity
import br.com.r7.calendario.core.dataproviders.entities.toEntity
import br.com.r7.calendario.core.dataproviders.entities.toUsuario
import br.com.r7.calendario.core.dataproviders.helpers.unwrap
import br.com.r7.calendario.usecases.gateway.AgendaRepository
import br.com.r7.calendario.usecases.gateway.UsuarioRepository
import org.springframework.stereotype.Repository

@Repository
class AgendaRepositoryImpl(private val dbAgendaRepository: DBAgendaRepository) : AgendaRepository {

    override fun salvar(agenda: Agenda) : Agenda {
        val usuarioEntity = agenda.toEntity()
        return this.dbAgendaRepository.save(usuarioEntity).unwrap(AgendaEntity::toUsuario)
    }

}