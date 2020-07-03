package br.com.r7.calendario.controller

import br.com.r7.calendario.dto.UsuarioDTO
import br.com.r7.calendario.dto.toUsuario
import br.com.r7.calendario.dto.toUsuarioDTO
import br.com.r7.calendario.usecases.exceptions.UsuarioJaCadastradoException
import br.com.r7.calendario.usecases.usuario.NovoUsuarioUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["api/usuarios"])
class UsuarioController(private val novoUsuarioUseCase: NovoUsuarioUseCase) {

    @PostMapping
    @Throws(UsuarioJaCadastradoException::class)
    fun salvar(@Valid @RequestBody usuarioDTO: UsuarioDTO) : ResponseEntity<UsuarioDTO>{
        val usuarioSalvo = this.novoUsuarioUseCase.execute(usuarioDTO.toUsuario())
        return ResponseEntity.ok(usuarioSalvo.toUsuarioDTO())
    }

}