package br.com.r7.calendario.core.dataproviders.repository.usuario

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.core.dataproviders.entities.UsuarioEntity
import br.com.r7.calendario.core.dataproviders.entities.toEntity
import br.com.r7.calendario.core.dataproviders.entities.toUsuario
import br.com.r7.calendario.core.dataproviders.helpers.unwrap
import br.com.r7.calendario.usecases.gateway.UsuarioRepository
import org.springframework.stereotype.Repository

@Repository
class UsuarioRepositoryImpl(private val dbUsuarioRepository: DBUsuarioRepository) : UsuarioRepository {

    override fun salvar(usuario: Usuario) : Usuario {
        val usuarioEntity = usuario.toEntity()
        return this.dbUsuarioRepository.save(usuarioEntity).unwrap(UsuarioEntity::toUsuario)
    }

    override fun isUsuarioCadastrado(login : String) : Boolean {
        return this.dbUsuarioRepository.existsByLogin(login)
    }

    override fun findByLogin(login: String): Usuario? {
        return this.dbUsuarioRepository.findByLogin(login).unwrap(UsuarioEntity::toUsuario)
    }

    override fun findById(id: Long): Usuario? {
        return this.dbUsuarioRepository.findById(id).unwrap(UsuarioEntity::toUsuario)
    }

    override fun isListaUsuariosCadastrados(logins: List<String>): Boolean {
        val usersFound = this.dbUsuarioRepository.countByLoginIn(logins)
        return usersFound.toInt() == logins.size
    }

}