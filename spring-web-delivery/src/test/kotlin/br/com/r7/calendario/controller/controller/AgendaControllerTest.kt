package br.com.r7.calendario.controller.controller

import br.com.r7.calendario.controller.AgendaController
import br.com.r7.calendario.dto.AgendaDTO
import br.com.r7.calendario.dto.toAgenda
import br.com.r7.calendario.handler.ExceptionHandler
import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders


class AgendaControllerTest {

    companion object {
        private const val PATH = "/api/agendas"
        private const val MESSAGE = "$.message"
    }

    private lateinit var agendaDTO: AgendaDTO
    private lateinit var mockMvc: MockMvc
    private lateinit var novaAgendaUseCase: NovaAgendaUseCase
    private val objectMapper = jacksonObjectMapper().findAndRegisterModules().enable(MapperFeature.USE_ANNOTATIONS)


    @BeforeEach
    fun init() {

        this.novaAgendaUseCase = mock()

        this.mockMvc = MockMvcBuilders.standaloneSetup(AgendaController(novaAgendaUseCase))
                .setControllerAdvice(ExceptionHandler())
                .build()

        this.agendaDTO = AgendaDTO(
                nome = "Agenda",
                descricao = "Agenda Teste",
                idUsuario = 1
        )
    }

    @Test
    fun testeCriarUsuario() {

        whenever(novaAgendaUseCase.execute(any())).thenReturn(this.agendaDTO.toAgenda().copy(id = 1))

        val agenda = this.objectMapper.writeValueAsString(agendaDTO)

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(agenda))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").exists())

        verify(novaAgendaUseCase).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoNomeEstaVazio() {

        val agenda = this.objectMapper.writeValueAsString(agendaDTO.copy(nome = ""))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(agenda))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.notBlank}"))

        verify(novaAgendaUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoDescricaoEstaVazio() {

        val agenda = this.objectMapper.writeValueAsString(agendaDTO.copy(descricao = ""))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(agenda))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.notBlank}"))

        verify(novaAgendaUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarUsuarioQuandoCampoDescricaoTemMaisDe255Caracteres() {

        val descricao = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam pharetra a est eget aliquet." +
                " Donec egestas, velit et feugiat laoreet, nisl ex tristique lorem, non auctor neque neque a ipsum. Donec dictum fermentum gravida." +
                " Curabitur fermentum turpis nam nam."

        val agenda = this.objectMapper.writeValueAsString(agendaDTO.copy(descricao = descricao))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(agenda))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.size.255}"))

        verify(novaAgendaUseCase, never()).execute(any())
    }

}