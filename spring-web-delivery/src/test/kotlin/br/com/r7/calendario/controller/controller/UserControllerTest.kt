package br.com.r7.calendario.controller.controller

import br.com.r7.calendario.controller.UsuarioController
import br.com.r7.calendario.dto.UsuarioDTO
import br.com.r7.calendario.dto.toUsuario
import br.com.r7.calendario.handler.ExceptionHandler
import br.com.r7.calendario.usecases.usuario.NovoUsuarioUseCase
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime


class UserControllerTest {

    companion object {
        private const val PATH = "/api/usuarios"
        private const val MESSAGE = "$.message"
    }

    private lateinit var usuarioDTO: UsuarioDTO
    private lateinit var mockMvc: MockMvc
    private lateinit var novoUsuarioUseCase: NovoUsuarioUseCase
    private val objectMapper = jacksonObjectMapper().findAndRegisterModules()

    @BeforeEach
    fun init() {

        this.novoUsuarioUseCase = mock()

        this.mockMvc = MockMvcBuilders.standaloneSetup(UsuarioController(novoUsuarioUseCase))
                .setControllerAdvice(ExceptionHandler()).build()

        this.usuarioDTO = UsuarioDTO(
                nome = "Usuario",
                senha = "SenHA",
                login = "login@email.com",
                dataCadastro = LocalDateTime.now()
        )
    }

    @Test
    fun testeCriarUsuario() {

        whenever(novoUsuarioUseCase.execute(any())).thenReturn(this.usuarioDTO.toUsuario().copy(id = 1))

        val usuario = this.objectMapper.writeValueAsString(usuarioDTO)
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(usuario))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.senha").isEmpty)

        verify(novoUsuarioUseCase).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoNomeEstaVazio() {

        val usuario = this.objectMapper.writeValueAsString(usuarioDTO.copy(nome = ""))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(usuario))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.notBlank}"))

        verify(novoUsuarioUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoLoginEstaVazio() {

        val usuario = this.objectMapper.writeValueAsString(usuarioDTO.copy(login = ""))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(usuario))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.notBlank}"))

        verify(novoUsuarioUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoLoginNaoEhUmEmail() {

        val usuario = this.objectMapper.writeValueAsString(usuarioDTO.copy(login = "login"))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(usuario))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.emailInvalid}"))

        verify(novoUsuarioUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoSenhaEstaVazio() {

        val usuario = this.objectMapper.writeValueAsString(usuarioDTO.copy(senha = ""))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(usuario))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.notBlank}"))

        verify(novoUsuarioUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoDataCadastroEhFutura() {

        val usuario = this.objectMapper.writeValueAsString(usuarioDTO.copy(dataCadastro = LocalDateTime.now().plusDays(1)))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(usuario))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.pastOrPresent}"))

        verify(novoUsuarioUseCase, never()).execute(any())
    }


}