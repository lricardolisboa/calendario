package br.com.r7.calendario.usecases.usuario

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.usecases.exceptions.UsuarioJaCadastradoException
import br.com.r7.calendario.usecases.exceptions.UsuarioNaoEncontradoException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginUsuarioUseCase(private val usuarioRepository: UsuarioRepository)  {

    @Throws(UsuarioJaCadastradoException::class)
    @Transactional
    fun findByLogin(username : String) : Usuario {
        return usuarioRepository.findByLogin(username) ?: throw UsuarioNaoEncontradoException("Login de usuário não existe")
    }

    fun findById(id: Long) : Usuario {
        return usuarioRepository.findById(id) ?: throw UsuarioNaoEncontradoException("Usuário não existe")
    }

    interface UsuarioRepository {
        fun findByLogin(login : String): Usuario?
        fun findById(id: Long): Usuario?
    }
}



