package br.com.r7.calendario.usecases

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import br.com.r7.calendario.usecases.exceptions.UsuarioExistenteException
import br.com.r7.calendario.usecases.gateway.AgendaRepository
import br.com.r7.calendario.usecases.gateway.UsuarioRepository
import br.com.r7.calendario.usecases.usuario.NovoUsuarioUseCase
import com.google.common.hash.Hashing
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.anyString
import java.nio.charset.StandardCharsets
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