package br.com.r7.calendario.usecases.usuario

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.usecases.exceptions.UsuarioNaoEncontradoException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import java.time.LocalDateTime
import kotlin.math.log

class LoginUsuarioUseCaseTest {

    private lateinit var usuario: Usuario
    private lateinit var usuarioRepository: LoginUsuarioUseCase.UsuarioRepository

    private lateinit var loginUsuarioUseCase : LoginUsuarioUseCase

    @BeforeEach
    fun init(){
        this.usuarioRepository = mock()
        this.loginUsuarioUseCase = LoginUsuarioUseCase(usuarioRepository)

        this.usuario = Usuario(id = 1L, nome = "nome", login = "email@email.com", senha = "", dataCadastro = LocalDateTime.now())
    }

    @Test
    fun testFindUserByLogin(){

        whenever(usuarioRepository.findByLogin(anyString()))
                .thenReturn(usuario)

        val usuarioLogin = this.loginUsuarioUseCase.findByLogin("email@email.com")
        assertNotNull(usuarioLogin)

        verify(usuarioRepository).findByLogin(anyString())

    }

    @Test
    fun testFindUserByLoginWhenUserNotExists(){

        whenever(usuarioRepository.findByLogin(anyString()))
                .thenReturn(null)

        assertThrows< UsuarioNaoEncontradoException> { this.loginUsuarioUseCase.findByLogin("email@email.com") }

        verify(usuarioRepository).findByLogin(anyString())

    }

    @Test
    fun testFindUserById(){

        whenever(usuarioRepository.findById(anyLong()))
                .thenReturn(usuario)

        val usuarioLogin = this.loginUsuarioUseCase.findById(1L)
        assertNotNull(usuarioLogin)

        verify(usuarioRepository).findById(anyLong())

    }

    @Test
    fun testFindUserByIdWhenUserNotExists(){

        whenever(usuarioRepository.findById(anyLong()))
                .thenReturn(null)

        assertThrows< UsuarioNaoEncontradoException> { this.loginUsuarioUseCase.findById(1L) }

        verify(usuarioRepository).findById(anyLong())

    }


}