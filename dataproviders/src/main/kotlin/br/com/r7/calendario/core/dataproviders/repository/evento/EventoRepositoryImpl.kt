package br.com.r7.calendario.core.dataproviders.repository.evento

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Evento
import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.core.dataproviders.entities.*
import br.com.r7.calendario.core.dataproviders.helpers.unwrap
import br.com.r7.calendario.usecases.gateway.AgendaRepository
import br.com.r7.calendario.usecases.gateway.EventoRepository
import br.com.r7.calendario.usecases.gateway.UsuarioRepository
import org.springframework.stereotype.Repository

@Repository
class EventoRepositoryImpl(private val dbEventoRepository: DBEventoRepository) : EventoRepository {
    override fun salvar(evento: Evento): Evento {
        val eventoEntity = evento.toEntity()
        return this.dbEventoRepository.save(eventoEntity).unwrap(EventoEntity::toEvento)
    }
}