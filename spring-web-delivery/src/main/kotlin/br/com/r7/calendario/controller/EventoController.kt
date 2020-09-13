package br.com.r7.calendario.controller

import br.com.r7.calendario.dto.EventoDTO
import br.com.r7.calendario.dto.toEvento
import br.com.r7.calendario.dto.toEventoDTO
import br.com.r7.calendario.security.UsuarioLogado
import br.com.r7.calendario.usecases.evento.NovoEventoUseCase
import br.com.r7.calendario.usecases.exceptions.UsuarioNaoEncontradoException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(value = ["api/eventos"])
class EventoController(private val novoEventoUseCase: NovoEventoUseCase) {

    @PostMapping
    @Throws(UsuarioNaoEncontradoException::class)
    fun salvar(@Valid @RequestBody eventoDTO: EventoDTO,  @AuthenticationPrincipal usuarioLogado: UsuarioLogado) : ResponseEntity<EventoDTO>{
        val eventoSalvo = this.novoEventoUseCase.salvar(eventoDTO.toEvento(usuarioLogado.id))
        return ResponseEntity.ok(eventoSalvo.toEventoDTO())
    }

}