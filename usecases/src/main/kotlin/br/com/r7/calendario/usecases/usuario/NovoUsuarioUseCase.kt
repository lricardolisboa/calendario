package br.com.r7.calendario.usecases.usuario

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import br.com.r7.calendario.usecases.exceptions.UsuarioJaCadastradoException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class NovoUsuarioUseCase(private val usuarioRepository: UsuarioRepository,
                         private val agendaUseCase: NovaAgendaUseCase)  {

    private val passwordEncoder = BCryptPasswordEncoder()

    @Throws(UsuarioJaCadastradoException::class)
    @Transactional
    fun execute(usuario : Usuario) : Usuario {
        if(usuarioRepository.isUsuarioCadastrado(usuario.login))
            throw UsuarioJaCadastradoException("Login já cadastrado no sistema")

        val senhaCriptografada = this.passwordEncoder.encode(usuario.senha)

        val usuarioSalvo = this.usuarioRepository.salvar(usuario.copy(dataCadastro = LocalDateTime.now(), senha = senhaCriptografada))

        this.agendaUseCase.inserirAgendaPadrao(usuarioSalvo.nome,usuarioSalvo.id!!)

        return usuarioSalvo
    }

    interface UsuarioRepository{
        fun salvar(usuario : Usuario): Usuario
        fun isUsuarioCadastrado(login : String): Boolean
    }
}



