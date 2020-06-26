package br.com.r7.calendario.core.dataproviders.repository.agenda

import br.com.r7.calendario.core.dataproviders.entities.AgendaEntity
import br.com.r7.calendario.core.dataproviders.entities.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DBAgendaRepository : JpaRepository<AgendaEntity, String>{


}