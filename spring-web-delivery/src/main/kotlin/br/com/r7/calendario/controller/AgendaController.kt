package br.com.r7.calendario.controller

import br.com.r7.calendario.dto.AgendaDTO
import br.com.r7.calendario.dto.toAgenda
import br.com.r7.calendario.dto.toAgendaDTO
import br.com.r7.calendario.security.UsuarioLogado
import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import br.com.r7.calendario.usecases.exceptions.UsuarioJaCadastradoException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(value = ["api/agendas"])
class AgendaController(private val novaAgendaUseCase: NovaAgendaUseCase) {

    @PostMapping
    @Throws(UsuarioJaCadastradoException::class)
    fun salvar(@Valid @RequestBody agendaDTO: AgendaDTO, @AuthenticationPrincipal usuarioLogado: UsuarioLogado) : ResponseEntity<AgendaDTO>{
        val agenda = agendaDTO.toAgenda(usuarioLogado.id)
        val usuarioSalvo = this.novaAgendaUseCase.execute(agenda)
        return ResponseEntity.ok(usuarioSalvo.toAgendaDTO())
    }

}