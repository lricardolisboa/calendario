package br.com.r7.calendario.core.dataproviders.repository.evento

import br.com.r7.calendario.core.dataproviders.entities.EventoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DBEventoRepository : JpaRepository<EventoEntity, Long> {
}