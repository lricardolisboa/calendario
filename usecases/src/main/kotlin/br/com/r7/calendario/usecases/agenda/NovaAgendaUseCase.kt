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

    fun inserirAgendaPadrao(nome: String, idUsuario: Long) {
        val agendaPadrao = Agenda(
                id = null,
                nome = nome,
                descricao = "Agenda $nome",
                idUsuario = idUsuario,
                dataCadastro = LocalDateTime.now()
        )

        this.execute(agendaPadrao)
    }

    interface AgendaRepository{
        fun salvar(agenda: Agenda): Agenda
    }

}