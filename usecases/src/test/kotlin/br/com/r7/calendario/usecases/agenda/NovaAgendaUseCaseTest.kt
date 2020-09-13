package br.com.r7.calendario.usecases.agenda

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.usecases.gateway.AgendaRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class NovaAgendaUseCaseTest {

    private lateinit var novaAgendaUseCase: NovaAgendaUseCase
    private lateinit var agenda: Agenda
    private lateinit var agendaRepository: AgendaRepository

    @BeforeEach
    fun init() {
        this.agendaRepository = mock()

        this.novaAgendaUseCase = NovaAgendaUseCase(agendaRepository)

        this.agenda = Agenda(id = null, nome = "Agenda", descricao = "Agenda de Ricardo", idUsuario = 1, dataCadastro = LocalDateTime.now())
    }

    @Test
    fun testeCriarNovaAgenda() {

        whenever(agendaRepository.salvar(any()))
                .thenReturn(this.agenda)

        this.novaAgendaUseCase.execute(this.agenda)

        verify(agendaRepository).salvar(any())

    }

}