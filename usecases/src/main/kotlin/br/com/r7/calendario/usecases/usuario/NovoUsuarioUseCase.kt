package br.com.r7.calendario.usecases.usuario

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.usecases.exceptions.UsuarioExistenteException
import com.google.common.hash.Hashing
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

@Service
class NovoUsuarioUseCase(private val usuarioRepository: UsuarioRepository)  {

    @Throws(UsuarioExistenteException::class)
    @Transactional
    fun execute(usuario : Usuario) : Usuario {
        if(usuarioRepository.isUsuarioCadastrado(usuario.login))
            throw UsuarioExistenteException("Login já cadastrado no sistema")

        val senhaCriptografada = Hashing.sha512().hashString(usuario.senha, StandardCharsets.UTF_8).toString()
        return this.usuarioRepository.salvar(usuario.copy(dataCadastro = LocalDateTime.now(),senha = senhaCriptografada))
    }

    interface UsuarioRepository{
        fun salvar(usuario : Usuario): Usuario
        fun isUsuarioCadastrado(login : String): Boolean
    }
}



