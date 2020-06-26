package br.com.r7.calendario.usecases.usuario

import br.com.r7.calendario.core.Agenda
import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import br.com.r7.calendario.usecases.exceptions.UsuarioExistenteException
import com.google.common.hash.Hashing
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

@Service
class NovoUsuarioUseCase(private val usuarioRepository: UsuarioRepository,
                            private val agendaUseCase: NovaAgendaUseCase)  {

    @Throws(UsuarioExistenteException::class)
    @Transactional
    fun execute(usuario : Usuario) : Usuario {
        if(usuarioRepository.isUsuarioCadastrado(usuario.login))
            throw UsuarioExistenteException("Login já cadastrado no sistema")

        val senhaCriptografada = Hashing.sha512().hashString(usuario.senha, StandardCharsets.UTF_8).toString()
        val usuarioSalvo = this.usuarioRepository.salvar(usuario.copy(dataCadastro = LocalDateTime.now(), senha = senhaCriptografada))

        this.agendaUseCase.inserirAgendaPadrao(usuarioSalvo.nome,usuarioSalvo.id!!)

        return usuarioSalvo
    }

    interface UsuarioRepository{
        fun salvar(usuario : Usuario): Usuario
        fun isUsuarioCadastrado(login : String): Boolean
    }
}



