package br.com.r7.calendario.usecases.usuario

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import br.com.r7.calendario.usecases.exceptions.UsuarioJaCadastradoException
import br.com.r7.calendario.usecases.gateway.UsuarioRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

class NovoUsuarioUseCaseTest {

    private lateinit var novoUsuarioUseCase: NovoUsuarioUseCase
    private lateinit var novaAgendaUseCase: NovaAgendaUseCase
    private lateinit var usuario: Usuario
    private lateinit var usuarioRepository: UsuarioRepository

    @BeforeEach
    fun init() {
        this.usuarioRepository = mock()
        this.novaAgendaUseCase = mock()

        this.novoUsuarioUseCase = NovoUsuarioUseCase(usuarioRepository,novaAgendaUseCase)

        this.usuario = Usuario(id = null, login = "email@email.com", nome = "usuario", senha = "seNha", dataCadastro = LocalDateTime.now())
    }

    @Test
    fun testeCriarNovoUsuario() {

        whenever(usuarioRepository.isUsuarioCadastrado(anyString()))
                .thenReturn(false)
        whenever(usuarioRepository.salvar(any()))
                .thenReturn(this.usuario.copy(id = 1))
        doNothing().whenever(novaAgendaUseCase).inserirAgendaPadrao(anyString(), anyLong())

        this.novoUsuarioUseCase.execute(this.usuario)

        verify(usuarioRepository).isUsuarioCadastrado(anyString())
        verify(usuarioRepository).salvar(argThat {  BCryptPasswordEncoder().matches(usuario.senha, senha) })
        verify(novaAgendaUseCase).inserirAgendaPadrao(anyString(), anyLong())

    }

    @Test
    fun testeCriarNovoUsuarioComLoginJaExistente() {

        whenever(usuarioRepository.isUsuarioCadastrado(anyString()))
                .thenReturn(true)

        assertThrows<UsuarioJaCadastradoException> ("Login já cadastrado no sistema") { this.novoUsuarioUseCase.execute(this.usuario) }

        verify(usuarioRepository).isUsuarioCadastrado(anyString())
        verify(usuarioRepository, never()).salvar(any())
        verify(novaAgendaUseCase, never()).inserirAgendaPadrao(anyString(), anyLong())

    }

}