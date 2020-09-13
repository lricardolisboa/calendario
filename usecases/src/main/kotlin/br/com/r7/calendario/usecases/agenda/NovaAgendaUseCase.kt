package br.com.r7.calendario.usecases.agenda

import br.com.r7.calendario.core.Agenda
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class NovaAgendaUseCase(private val agendaRepository: AgendaRepository) {

    @Transactional
    fun execute(agenda: Agenda) : Agenda {
        return this.agendaRepository.salvar(agenda.copy(dataCadastro = LocalDateTime.now()))
    }

    interface AgendaRepository{
        fun salvar(agenda: Agenda): Agenda
    }

}