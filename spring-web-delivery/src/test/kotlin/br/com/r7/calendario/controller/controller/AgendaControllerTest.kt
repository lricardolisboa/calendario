package br.com.r7.calendario.controller.controller

import br.com.r7.calendario.controller.AgendaController
import br.com.r7.calendario.dto.AgendaDTO
import br.com.r7.calendario.dto.toAgenda
import br.com.r7.calendario.handler.ExceptionHandler
import br.com.r7.calendario.security.TokenManager
import br.com.r7.calendario.security.UsuarioLogado
import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


class AgendaControllerTest {

    companion object {
        private const val PATH = "/api/agendas"
        private const val MESSAGE = "$.message"
    }

    private lateinit var token: String
    private lateinit var agendaDTO: AgendaDTO
    private lateinit var mockMvc: MockMvc
    private lateinit var novaAgendaUseCase: NovaAgendaUseCase
    private val objectMapper = jacksonObjectMapper().findAndRegisterModules().enable(MapperFeature.USE_ANNOTATIONS)

    class UsuarioLogadoResolver : HandlerMethodArgumentResolver{
        override fun supportsParameter(parameter: MethodParameter): Boolean {
            return parameter.parameterType.isAssignableFrom(UsuarioLogado::class.javaObjectType);
        }

        override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
            return UsuarioLogado(id = 1, senha = "senha", nome = "Usuario");
        }

    }

    @BeforeEach
    fun init() {

        this.novaAgendaUseCase = mock()

        this.mockMvc = MockMvcBuilders.standaloneSetup(AgendaController(novaAgendaUseCase))
                .setControllerAdvice(ExceptionHandler())
                .setCustomArgumentResolvers(UsuarioLogadoResolver())
                .build()

        this.agendaDTO = AgendaDTO(
                nome = "Agenda",
                descricao = "Agenda Teste"
        )

    }

    @Test
    fun testeCriarAgenda() {

        whenever(novaAgendaUseCase.execute(any())).thenReturn(this.agendaDTO.toAgenda(1).copy(id = 1))

        val agenda = this.objectMapper.writeValueAsString(agendaDTO)

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                .content(agenda))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").exists())

        verify(novaAgendaUseCase).execute(any())
    }

    @Test
    fun testeCriarAgendaQuandoCampoNomeEstaVazio() {

        val agenda = this.objectMapper.writeValueAsString(agendaDTO.copy(nome = ""))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(agenda))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.notBlank}"))

        verify(novaAgendaUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarAgendaQuandoCampoDescricaoEstaVazio() {

        val agenda = this.objectMapper.writeValueAsString(agendaDTO.copy(descricao = ""))

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(agenda))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath(MESSAGE).value("{validation.notBlank}"))

        verify(novaAgendaUseCase, never()).execute(any())
    }

    @Test
    fun testeCriarAgendaQuandoCampoDescricaoTemMaisDe255Caracteres() {

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